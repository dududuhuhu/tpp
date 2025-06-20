from service.configure import *
from mq.consumer import Consumer
from mq.publisher import Publisher
from service import *
# from work.HeartCheck import HeartCheck
# from system.SystemInfo import SystemInfo
# from mq.RabbitMQ import RabbitMQ
from service.loginService import LoginService
from time import sleep

def login():
    agent_key_pair = SignKeyPair()
    agent_key_pair.load_pri(USER_PEM_PRI)
    l = LoginService(server_pub_key=SERVER_PEM_PUB, amqp_url=get_amqp_url(HOST, PORT, USERNAME, PASSWORD, VHOST), \
                    login_exchange='sysinfo_exchange', login_routing_key='sysinfo', login_recv_exchange=f'agent_{MAC.replace(":", "")}_exchange', login_recv_queue=f'agent_{MAC.replace(":", "")}_queue', \
                    login_recv_routing_key=MAC.replace(":",""), heartbeat_exchange='sysinfo', heartbeat_queue='status_queue', heartbeat_routing_key='status', \
                    agent_key_pair=agent_key_pair)
    l.start()
    return l

def startup():
    for routing_info in CONSUMER_ROUTING:
        routing_info.set_callback(routing_info.wrap_callback(default_consumer, default_publisher))
    default_consumer.add_queue_key_pairs(CONSUMER_ROUTING)
    default_publisher.add_queue_key_pairs(PUBLISHER_ROUTING)
    default_consumer.start()
    default_publisher.start()

def join(l):
    default_consumer.join()
    default_publisher.join()
    l.join()

def main():
    l = login()
    startup()
    join(l)

if __name__ == "__main__":
    main()