from mq.publisher import Publisher
from mq import get_amqp_url
import threading
import time

def main():
    url = get_amqp_url('172.17.0.2', 5672, 'admin', 'hello123456', 'my_vhost')
    publisher = Publisher(exchange='sysinfo_exchange', amqp_url=url)
    routing = {
        'test_queue':'test',
        'test1_queue':'test1'
    }
    publisher.add_queue_key_pairs(routing)
    # publisher.run()
    t = threading.Thread(target=publisher.run)
    t.start()
    while True:
        time.sleep(10)  # Wait for the connection to be established
        publisher.publish_message("test", "hello, test")
        publisher.publish_message("test1", "hello, test1")

if __name__ == "__main__":
    main()