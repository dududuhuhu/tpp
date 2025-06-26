from mq.consumer import Consumer
import uuid
import platform as _platform
from service.consumerService import agent_mac_queue_callback
from Logs.logRuleSave import save_log_rule
all = ['HOST', 'PORT', 'USERNAME', 'PASSWORD', 'VHOST', 'consumer_routing', 'publisher_routing']
HOST = '172.17.0.2'
PORT = 5672
USERNAME = 'admin'
PASSWORD = 'hello123456'
VHOST = 'my_vhost'

MAC = ':'.join(("%012X" % uuid.getnode())[i:i + 2] for i in range(0, 12, 2))
if _platform.system() == "Linux":
    PLATFORM = "Linux"
elif _platform.system() == "Windows":
    PLATFORM = "Windows"
else:
    PLATFORM = "Unknown"

CONSUMER_EXCHANGE_NAME = f'agent_{MAC.replace(":", "")}_exchange'
PUBLISHER_EXCHANGE_NAME = 'sysinfo_exchange'

SERVER_PEM_PUB = '-----BEGIN PUBLIC KEY-----\nMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEhqAtQ5IZqhrUo/7W1K5NTZA6y5DUPVGMxdVtGi6Z4WXGv5NwAaz4T5pFs3rOBEz2S5dvKBn7PJ5NeSWd8mNijA==\n-----END PUBLIC KEY-----\n'


CONSUMER_ROUTING = [
    Consumer.RoutingInfo(
        queue_name=f'agent_{MAC.replace(":", "")}_queue',
        routing_key=MAC.replace(":", ""),
        callback=agent_mac_queue_callback
    ),
    Consumer.RoutingInfo(
        queue_name=f'inTime_{MAC.replace(':', '')}_queue',
        routing_key=f'{MAC.replace(":", "")}Rule',
        callback=save_log_rule,
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
    'auditLog_queue':'auditLog',
    'loginLog_queue':'loginLog',
    'accountChangeLog_queue':'accountChangeLog',
    'inTime_queue':'inTime',
    'inTimeRequest_queue':'inTimeRequest',
    'baselineDetect_queue':'baselineDetect',
    'baselineHardening_queue':'baselineHardening',
}

