from mq.consumer import Consumer
import uuid
from service.consumerService import agent_mac_queue_callback
all = ['HOST', 'PORT', 'USERNAME', 'PASSWORD', 'VHOST', 'consumer_routing', 'publisher_routing']
HOST = '192.168.192.128'
PORT = 4568
USERNAME = 'admin'
PASSWORD = '20250606'
VHOST = 'my_vhost'

MAC = ':'.join(("%012X" % uuid.getnode())[i:i + 2] for i in range(0, 12, 2))
CONSUMER_EXCHANGE_NAME = f'agent_{MAC.replace(":", "")}_exchange'
PUBLISHER_EXCHANGE_NAME = 'sysinfo_exchange'

CONSUMER_ROUTING = [
    Consumer.RoutingInfo(
        queue_name=f'agent_{MAC.replace(":", "")}_queue',
        routing_key=MAC.replace(":", ""),
        callback=agent_mac_queue_callback
    )
]

PUBLISHER_ROUTING = {
    'hotfix_queue':'hotfix',
    'applicationRisk_queue':'applicationRisk',
    'password_queue':'password',
    'systemRisk_queue':'systemRisk',
    'vulnerability_queue':'vulnerability',
    'account_queue':'account',
    'service_queue':'service',
    'process_queue':'process',
    'app_queue':'app',
}

