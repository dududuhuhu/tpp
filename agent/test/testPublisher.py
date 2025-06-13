from mq.publisher import Publisher
from mq import get_amqp_url
import functools
import time

def main():
    url = get_amqp_url('172.17.0.2', 5672, 'admin', 'hello123456', 'my_vhost')
    publisher = Publisher(exchange='sysinfo_exchange', amqp_url=url)
    routing = {
        'test_queue': 'test',
        'test1_queue': 'test1'
    }
    publisher.add_queue_key_pairs(routing)
    publisher.start()
    while True:
        time.sleep(2)
        message = 'test publisher'
        publisher.publish_message(routing_key='test', message=message)
        publisher.publish_message(routing_key='test1', message=message)

if __name__ == "__main__":
    main()
