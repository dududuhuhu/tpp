# -*- coding: utf-8 -*-
# pylint: disable=C0111,C0103,R0205

import functools
import logging
import time
import pika
from pika.exchange_type import ExchangeType

from utils import logger as LOGGER
from mq.service import Service
from utils.crypto.src.asymmetric import SignKeyPair


class Consumer(Service):
    """This is an example consumer that will handle unexpected interactions
    with RabbitMQ such as channel and connection closures.

    If RabbitMQ closes the connection, this class will stop and indicate
    that reconnection is necessary. You should look at the output, as
    there are limited reasons why the connection may be closed, which
    usually are tied to permission related issues or socket timeouts.

    If the channel is closed, it will indicate a problem with one of the
    commands that were issued and that should surface in the output as well.

    """

    class RoutingInfo:
        def __init__(self, queue_name:str, routing_key:str, callback):
            self._queue:str = queue_name
            self._routing_key:str = routing_key
            self._callback = callback
            self._consumer_tag = None
        
        def wrap_callback(self, consumer, publisher):
            """Wrap the callback with the consumer instance."""
            return functools.partial(self._callback, consumer, publisher)
        
        def set_callback(self, callback):
            self._callback = callback

    def __init__(self, exchange, amqp_url, verify_key_pair:SignKeyPair, exchange_type=ExchangeType.direct):
        super().__init__(exchange, amqp_url, exchange_type)

        self._routing_infos:list[Consumer.RoutingInfo] = []
        
        self._prefetch_count = 1

        self._verify_key_pair = verify_key_pair

    def add_queue_key_pairs(self, routingInfos:list[RoutingInfo]):
        for routingInfo in routingInfos:
            self._queue_key_pairs[routingInfo._queue] = (routingInfo._routing_key, False)
        self._routing_infos = routingInfos

    def _app_on_bindok(self, _unused_frame, userdata):
        print('bindok')
        self.set_qos()

    def set_qos(self):
        """This method sets up the consumer prefetch to only be delivered
        one message at a time. The consumer must acknowledge this message
        before RabbitMQ will deliver another one. You should experiment
        with different prefetch values to achieve desired performance.

        """
        self._channel.basic_qos(
            prefetch_count=self._prefetch_count, callback=self.on_basic_qos_ok)

    def on_basic_qos_ok(self, _unused_frame):
        """Invoked by pika when the Basic.QoS method has completed. At this
        point we will start consuming messages by calling start_consuming
        which will invoke the needed RPC commands to start the process.

        :param pika.frame.Method _unused_frame: The Basic.QosOk response frame

        """
        LOGGER.info('QOS set to: %d', self._prefetch_count)
        self._start_consuming()

    def _start_consuming(self):
        """This method sets up the consumer by first calling
        add_on_cancel_callback so that the object is notified if RabbitMQ
        cancels the consumer. It then issues the Basic.Consume RPC command
        which returns the consumer tag that is used to uniquely identify the
        consumer with RabbitMQ. We keep the value to use it when we want to
        cancel consuming. The on_message method is passed in as a callback pika
        will invoke when a message is fully received.

        """
        LOGGER.info('Issuing consumer related RPC commands')
        self.add_on_cancel_callback()
        for routingInfo in self._routing_infos:
            routingInfo._consumer_tag = self._channel.basic_consume(
                queue=routingInfo._queue,
                on_message_callback=routingInfo._callback,
            )

    def add_on_cancel_callback(self):
        """Add a callback that will be invoked if RabbitMQ cancels the consumer
        for some reason. If RabbitMQ does cancel the consumer,
        on_consumer_cancelled will be invoked by pika.

        """
        LOGGER.info('Adding consumer cancellation callback')
        self._channel.add_on_cancel_callback(self.on_consumer_cancelled)

    def on_consumer_cancelled(self, method_frame):
        """Invoked by pika when RabbitMQ sends a Basic.Cancel for a consumer
        receiving messages.

        :param pika.frame.Method method_frame: The Basic.Cancel frame

        """
        LOGGER.info('Consumer was cancelled remotely, shutting down: %r',
                    method_frame)
        self._channel.close()
        self._stop()

    def acknowledge_message(self, delivery_tag):
        """Acknowledge the message delivery from RabbitMQ by sending a
        Basic.Ack RPC method for the delivery tag.

        :param int delivery_tag: The delivery tag from the Basic.Deliver frame

        """
        LOGGER.info('Acknowledging message %s', delivery_tag)
        self._channel.basic_ack(delivery_tag)
