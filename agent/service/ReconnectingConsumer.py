from service.Consumer import Consumer
import time
import threading

from service import LOGGER

class ReconnectingConsumer(threading.Thread):
    """This is an example consumer that will reconnect if the nested
    ExampleConsumer indicates that a reconnect is necessary.

    """

    def __init__(self, amqp_url, exchange, routing_key, queue, handler=None, handler_args=()):
        super().__init__()
        self._handler = handler
        self._handler_args = handler_args
        self._exchange = exchange
        self._routing_key = routing_key
        self._queue_name = queue
        self._reconnect_delay = 0
        self._amqp_url = amqp_url
        self._consumer = Consumer(self._amqp_url, exchange=exchange, 
                                  routing_key=routing_key, queue=queue, handler=handler, handler_arguments=handler_args)

    def run(self):
        while True:
            try:
                self._consumer.run()
            except KeyboardInterrupt:
                self._consumer.stop()
                break
            self._maybe_reconnect()

    def _maybe_reconnect(self):
        if self._consumer.should_reconnect:
            self._consumer.stop()
            reconnect_delay = self._get_reconnect_delay()
            LOGGER.info('Reconnecting after %d seconds', reconnect_delay)
            time.sleep(reconnect_delay)
            self._consumer = Consumer(self._amqp_url, 
                                      exchange=self._exchange,
                                      routing_key=self._routing_key,
                                      queue=self._queue_name,
                                      handler=self._handler,
                                      handler_arguments=self._handler_args)

    def _get_reconnect_delay(self):
        if self._consumer.was_consuming:
            self._reconnect_delay = 0
        else:
            self._reconnect_delay += 1
        if self._reconnect_delay > 30:
            self._reconnect_delay = 30
        return self._reconnect_delay