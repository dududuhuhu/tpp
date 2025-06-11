from mq.RabbitMQ import RabbitMQ
from pojo.sysinfo import SysInfo
from service.UpdateStatus import UpdateStatus
from mq import default_mq
from service.ReconnectingConsumer import ReconnectingConsumer
from time import sleep
from urllib.parse import quote_plus
from service.AssetsDiscovery import AssetsDiscoveryService
from pojo import default_sysinfo

ACCOUNT = 'admin'
PASSWORD = 'hello123456'
HOST = 'my_vhost'
IP = "172.17.0.2"
PORT = 5672

def main():
    default_mq.publish(default_sysinfo.dump(), routing_key='sysinfo')
    routing_key = default_sysinfo.get_mac_address().replace(':', '')
    queue_name = f'agent_{routing_key}_queue'
    amqp_url = f"amqp://{ACCOUNT}:{quote_plus(PASSWORD)}@{IP}:{PORT}/{quote_plus(HOST)}"
    print(amqp_url)
    adservice = AssetsDiscoveryService()
    consumer = ReconnectingConsumer(amqp_url, exchange='agent_exchange', routing_key=routing_key, queue=queue_name, handler=AssetsDiscoveryService.callback, handler_args=(adservice,))
    consumer.start()
    u = UpdateStatus(default_sysinfo.get_mac_address(), default_mq)
    u.start()


if __name__ == "__main__":
    main()
