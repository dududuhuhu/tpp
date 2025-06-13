from service.configure import *
from mq.consumer import Consumer
from mq.publisher import Publisher
from service import *


def startup():
    for routing_info in CONSUMER_ROUTING:
        routing_info.set_callback(routing_info.wrap_callback(default_consumer, default_publisher))
    default_consumer.add_queue_key_pairs(CONSUMER_ROUTING)
    default_publisher.add_queue_key_pairs(PUBLISHER_ROUTING)
    default_consumer.start()
    default_publisher.start()

def join():
    default_consumer.join()
    default_publisher.join()

def main():
    startup()
    join()

if __name__ == "__main__":
    main()