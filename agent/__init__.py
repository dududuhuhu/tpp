from mq.consumer import Consumer
from mq.publisher import Publisher
from mq import get_amqp_url
from configure import *

url = get_amqp_url(HOST, PORT, USERNAME, PASSWORD, VHOST)
default_consumer = Consumer(exchange=CONSUMER_EXCHANGE_NAME, amqp_url=url)
default_publisher = Publisher(exchange=PUBLISHER_EXCHANGE_NAME, amqp_url=url)
default_consumer.add_queue_key_pairs(CONSUMER_ROUTING)
default_publisher.add_queue_key_pairs(PUBLISHER_ROUTING)


