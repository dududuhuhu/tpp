import json
from mq.consumer import Consumer
from mq.publisher import Publisher
# from RiskDiscovery.hotfix import Hotfix
# from RiskDiscovery.ApplicationRisk import ApplicationRisk
# from RiskDiscovery.password import Password
# from RiskDiscovery.SystemRisk import SystemRisk
# from RiskDiscovery.vulnerability import Vulnerability

def agent_mac_queue_callback(consumer:Consumer, publisher:Publisher, channel, basic_deliver, properties, body):
    try:
        data = json.loads(body.decode('utf-8'))
        print(f"Received message: {data}")
        if data['type'] == 'hotfix':
            pass
        elif data['type'] == 'applicationRisk':
            pass
        elif data['type'] == 'password':
            pass
        elif data['type'] == 'systemRisk':
            pass
        elif data['type'] == 'vulnerability':
            pass
        else:
            print(f"Unknown message type: {data['type']}")

    except json.JSONDecodeError as e:
        print(f"Failed to decode JSON: {e}")
    except Exception as e:
        print(f"An error occurred: {e}")
    finally:
        consumer.acknowledge_message(basic_deliver.delivery_tag)