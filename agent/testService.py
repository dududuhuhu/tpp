from mq.service import Service
from mq import get_amqp_url

class TestService(Service):
    def __init__(self, exchange, amqp_url):
        super().__init__(exchange, amqp_url)
    
    def _on_bindok(self, frame, userdata):
        super()._on_bindok(frame, userdata)
        for queue, (routing_key, bound) in self._queue_key_pairs.items():
            if not bound:
                return
        print("All queues are bound successfully.")

def main():
    url = get_amqp_url('172.17.0.2', 5672, 'admin', 'hello123456', 'my_vhost')
    test_service = TestService(exchange='sysinfo_exchange', amqp_url=url)
    routing = {
        'test_queue': 'test',
        'test1_queue': 'test1'
    }
    test_service.add_queue_key_pairs(routing)
    test_service.run()

if __name__ == "__main__":
    main()

