from threading import Lock
import json
import pika
from pika.exchange_type import ExchangeType
from threading import Lock
from mq.service import Service

from utils import logger

class Publisher(Service):
    def __init__(self, exchange, amqp_url, exchange_type=ExchangeType.direct):
        super().__init__(exchange, amqp_url, exchange_type)
        self._deliveries = None
        self._acked = None
        self._nacked = None
        self._message_number = None

        self._publish_lock:Lock = Lock()
    
    # def _on_bindok(self, frame):
    #     super()._on_bindok(frame)
    #     for queue, (routing_key, bound) in self._queue_key_pairs.items():
    #         if not bound:
    #             logger.warning("Queue %s is not bound to routing key %s", queue, routing_key)
    #             return
    #     self._start_publishing()

    def _app_on_bindok(self, frame, userdata):
        print('bindok')
        self._start_publishing()
    
    def _start_publishing(self):
        logger.info("Starting publishing")
        self._enable_delivery_confirmations()
    
    def _app_on_start(self):
        self._deliveries = {}
        self._acked = 0
        self._nacked = 0
        self._message_number = 0

    def _enable_delivery_confirmations(self):
        logger.info("Enabling delivery confirmations")
        self._channel.confirm_delivery(self._on_delivery_confirmation)
    
    def _on_delivery_confirmation(self, method_frame):
        confirmation_type = method_frame.method.NAME.split('.')[1].lower()
        ack_multiple = method_frame.method.multiple
        delivery_tag = method_frame.method.delivery_tag

        logger.info('Received %s for delivery tag: %i (multiple: %s)',
                    confirmation_type, delivery_tag, ack_multiple)

        if confirmation_type == 'ack':
            self._acked += 1
        elif confirmation_type == 'nack':
            self._nacked += 1

        del self._deliveries[delivery_tag]

        if ack_multiple:
            for tmp_tag in list(self._deliveries.keys()):
                if tmp_tag <= delivery_tag:
                    self._acked += 1
                    del self._deliveries[tmp_tag]
        logger.info(
            'Published %i messages, %i have yet to be confirmed, '
            '%i were acked and %i were nacked', self._message_number,
            len(self._deliveries), self._acked, self._nacked)
    
    def publish_message(self, routing_key, message:str, hdrs:dict=None):
        with self._publish_lock:
            if self.is_stopped():
                logger.warning("Channel is not open or stopping, cannot publish message")
                return
            
            properties = pika.BasicProperties(headers=hdrs, content_type='application/json')
            self._channel.basic_publish(
                exchange=self._exchange,
                routing_key=routing_key,
                body=json.dumps(message, ensure_ascii=False),
                properties=properties
            )
            self._message_number += 1
            self._deliveries[self._message_number] = True
            logger.info("Published message: %s", message)
    

