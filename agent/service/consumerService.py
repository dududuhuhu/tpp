import json
from mq.consumer import Consumer
from mq.publisher import Publisher
from work.HotfixDetect import HotfixDetector
from work.ApplicationRiskDetect import ApplicationRiskDetect
from work.PasswordDetect import SMBWeakPasswordScanner
from work.SystemRiskDetect import SystemRiskDetect
from work.VulnerabilityDetect import VulnerabilityDetect
from threading import Thread

def wrapper(routing_key, detector, publisher, need_publish):
    if need_publish:
        publisher.publish_message(routing_key=routing_key, message=detector.detect())

def agent_mac_queue_callback(consumer:Consumer, publisher:Publisher, channel, basic_deliver, properties, body):
    try:
        print(body.decode('utf-8'))
        data = json.loads(body.decode('utf-8'))
        print(f"Received message: {data}")
        detector = None
        routing_key = data['type']
        if data['type'] == 'hotfix':
            detector = HotfixDetector(body)
        elif data['type'] == 'applicationRisk':
            detector = ApplicationRiskDetect(body)
        elif data['type'] == 'password':
            detector = SMBWeakPasswordScanner(body)
        elif data['type'] == 'systemRisk':
            detector = SystemRiskDetect(body)
        elif data['type'] == 'vulnerability':
            detector = VulnerabilityDetect(body)
        else:
            print(f"Unknown message type: {data['type']}")
        if detector:
            t = Thread(target=wrapper, args=(routing_key, detector, publisher, True))
            t.start()

    except json.JSONDecodeError as e:
        print(f"Failed to decode JSON: {e}")
    except Exception as e:
        print(f"An error occurred: {e}")
    finally:
        consumer.acknowledge_message(basic_deliver.delivery_tag)