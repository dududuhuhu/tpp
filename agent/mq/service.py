import pika
import pika.channel

from utils import logger
from pika.exchange_type import ExchangeType
from threading import Lock, Thread
import functools


class Service(Thread):
    def __init__(self, exchange, amqp_url, exchange_type=ExchangeType.direct):
        super().__init__(daemon=True)
        self._exchange = exchange
        self._amqp_url = amqp_url
        self._exchange_type = exchange_type

        self.should_reconnect = True

        self._connection:pika.SelectConnection = None
        self._channel:pika.channel.Channel = None
        self._stopping = False
        self._lock = Lock()
        self._queue_key_pairs = {}
    
    def add_queue_key_pairs(self, queue_key_pairs:dict[str, str]):
        """
        queue_key_pairs format:
            key: queue name
            value: routing key
        """
        for queue, routing_key in queue_key_pairs.items():
            self._queue_key_pairs[queue] = (routing_key, False)
    
    def _connect(self):
        logger.info("Connecting to AMQP server at %s", self._amqp_url)
        return pika.SelectConnection(pika.URLParameters(self._amqp_url),
                                     on_open_callback=self._on_connection_open,
                                     on_open_error_callback=self._on_connection_open_error,
                                     on_close_callback=self._on_connection_closed)

    def _close_connection(self):
        if self._connection.is_open:
            self._connection.close()
    
    def stop(self):
        with self._lock:
            if not self._stopping:
                logger.info("Stopping service")
                self._stopping = True
            else:
                logger.warning("Connection is already closed")

    def _on_connection_open(self, connection):
        logger.info("Connection opened")
        self._open_channel()
    
    def _on_connection_open_error(self, connection, error):
        logger.error("Connection open error: %s", error)
        self._reconnect()

    def _on_connection_closed(self, connection, reason):
        print('connection closed')
        self._channel = None
        with self._lock:
            if self._stopping:
                self._stop()
            else:
                logger.warning("Connection closed, reason %s", reason)
                self._reconnect()
    
    def _reconnect(self):
        logger.info("Reconnecting to AMQP server")
        self.should_reconnect = True
        self._stop()
    
    def _stop(self):
        self._connection.ioloop.stop()

    
    def _open_channel(self):
        logger.info("Opening channel")
        self._connection.channel(on_open_callback=self._on_channel_open)
    
    def _on_channel_open(self, channel):
        logger.info("Channel opened")
        self._channel = channel
        self._add_on_channel_close_callback()
        self._setup_exchange(self._exchange)
    
    def _add_on_channel_close_callback(self):
        logger.info("Adding channel close callback")
        self._channel.add_on_close_callback(self._on_channel_closed)
    
    def _on_channel_closed(self, channel, reason):
        logger.warning("Channel %i was closed: %s", channel, reason)
        # Close the connection if the channel is closed, if connection is still alive, 
        # the method will be invoked again, and the connection.close() will also be invoked
        # again, but since there is a condition branch, deadloop will not happen.
        self._close_connection()
        self._reconnect()
    
    def _setup_exchange(self, exchange):
        logger.info("Declaring exchange: %s", exchange)
        cb = functools.partial(self._on_exchange_declareok, userdata=exchange)
        self._channel.exchange_declare(exchange=exchange,
                                       exchange_type=self._exchange_type,
                                       callback=cb, durable=True)

    def _on_exchange_declareok(self, frame, userdata):
        logger.info("Exchange declared: %s", userdata)
        for queue, _ in self._queue_key_pairs.items():
            self._setup_queue(queue)
    
    def _setup_queue(self, queue):
        logger.info("Declaring queue: %s", queue)
        cb = functools.partial(self._on_queue_declareok, userdata=queue)
        self._channel.queue_declare(queue=queue, callback=cb, durable=True)
    
    def _on_queue_declareok(self, frame, userdata):
        queue_name = userdata
        logger.info("Binding %s to %s with %s", self._exchange, queue_name, self._queue_key_pairs[queue_name][0])
        cb = functools.partial(self._on_bindok, userdata=queue_name)
        self._channel.queue_bind(queue_name, self._exchange, 
                                 self._queue_key_pairs[queue_name][0],
                                 callback=cb)

    def _on_bindok(self, frame, userdata):
        # self._bind_count += 1
        # if self._bind_count < len(self._queue_key_pairs):
        #     return
        # self._start_publishing()
        queue_name = userdata
        logger.info("Queue %s bound to exchange %s", queue_name, self._exchange)
        self._queue_key_pairs[queue_name] = (self._queue_key_pairs[queue_name][0], True)
        for queue, (routing_key, bound) in self._queue_key_pairs.items():
            if not bound:
                return
        self._app_on_bindok(frame, userdata)
    
    def _app_on_bindok(self, frame, userdata):
        pass
    
    def is_stopped(self):
        with self._lock:
            return not self._channel or self._stopping
    
    def _app_on_start(self):
        return
    
    def run(self):
        while True:
            with self._lock:
                if self._stopping:
                    logger.info("Service is stopping")
                    break
            if self.should_reconnect:
                self._app_on_start()
                self._connection = None
                self._channel = None
                self._connection = self._connect()
                self.should_reconnect = False
            else:
                break
            try:
                self._connection.ioloop.start()
            except pika.exceptions.ConnectionClosedByBroker as e:
                logger.error("Connection closed by broker: %s", e)
                self.should_reconnect = True
            except pika.exceptions.AMQPConnectionError as e:
                logger.error("AMQP connection error: %s", e)
                self.should_reconnect = True
            except Exception as e:
                logger.error("Unexpected error: %s", e)
                self.should_reconnect = True
