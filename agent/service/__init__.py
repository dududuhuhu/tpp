from mq.consumer import Consumer
from mq.publisher import Publisher
from mq import get_amqp_url
from service.configure import *

all = ['default_consumer', 'default_publisher']
url = get_amqp_url(HOST, PORT, USERNAME, PASSWORD, VHOST)
default_consumer = Consumer(exchange=CONSUMER_EXCHANGE_NAME, amqp_url=url)
default_publisher = Publisher(exchange=PUBLISHER_EXCHANGE_NAME, amqp_url=url)


