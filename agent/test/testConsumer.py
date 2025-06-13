from mq.consumer import Consumer
from mq import get_amqp_url
import functools

def test_callback(consumer:Consumer, channel, basic_deliver, properties, body):
    print(f"Received message: {body.decode('utf-8')}, "
          f"routing key: {basic_deliver.routing_key}, "
          f"delivery tag: {basic_deliver.delivery_tag}")
    consumer.acknowledge_message(basic_deliver.delivery_tag)
    

def main():
    url = get_amqp_url('172.17.0.2', 5672, 'admin', 'hello123456', 'my_vhost')
    consumer:Consumer = Consumer(exchange='sysinfo_exchange', amqp_url=url)
    test_cb = functools.partial(test_callback, consumer)
    routing = [
        Consumer.RoutingInfo(
            queue_name='test_queue',
            routing_key='test',
            callback=test_cb
        ),
        Consumer.RoutingInfo(
            queue_name='test1_queue',
            routing_key='test1',
            callback=test_cb
        )
    ]
    consumer.add_queue_key_pairs(routing)
    consumer.run()
    consumer.join()

if __name__ == "__main__":
    main()