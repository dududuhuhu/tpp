import json
from mq.consumer import Consumer
from mq.publisher import Publisher
from work.HotfixDetect import HotfixDetector
from work.ApplicationRiskDetect import ApplicationRiskDetect
from work.PasswordDetect import SMBWeakPasswordScanner
from work.SystemRiskDetect import SystemRiskDetect
from work.VulnerabilityDetect import VulnerabilityDetect
from work.AssetsDetect import *
from threading import Thread
from ..Logs.logs import audit_user_activity, analyze_login_logs,audit_account_changes

def wrapper(routing_key, detector, publisher, need_publish):
    if need_publish:
        publisher.publish_message(routing_key=routing_key, message=detector.detect())
    else:
        detector.detect()

def agent_mac_queue_callback(consumer:Consumer, publisher:Publisher, channel, basic_deliver, properties, body):
    try:
        print(body.decode('utf-8'))
        data = json.loads(body.decode('utf-8'))
        print(f"Received message: {data}")
        detector = None
        routing_key = data['type']
        if data['type'] == 'hotfix':
            detector = HotfixDetector(data)
        elif data['type'] == 'applicationRisk':
            detector = ApplicationRiskDetect(data)
        elif data['type'] == 'password':
            detector = SMBWeakPasswordScanner(data)
        elif data['type'] == 'systemRisk':
            detector = SystemRiskDetect(data)
        elif data['type'] == 'vulnerability':
            detector = VulnerabilityDetect(data)
        elif data['type'] == 'assets':
            if data['account'] == 1:
                detector = AcountDetector(data)
                t = Thread(target=wrapper, args=('account', detector, publisher, True))
                t.start()
            if data['service'] == 1:
                detector = ServiceDetector(data)
                t = Thread(target=wrapper, args=('service', detector, publisher, True))
                t.start()
            if data['process'] == 1:
                detector = ProcessDetector(data)
                t = Thread(target=wrapper, args=('process', detector, publisher, True))
                t.start()
            if data['app'] == 1:
                detector = AppDetector(data)
                t = Thread(target=wrapper, args=('app', detector, publisher, True))
                t.start()
            detector = None
        #     需要更新消费队列
        elif data['type'] == 'auditLog':
            from datetime import datetime, timedelta

            end_time = datetime.now()
            start_time = end_time - timedelta(hours=24)
            path = r"C:\Windows\System32\winevt\Logs\Security.evtx"
            suspicious_accounts = ["test1", "guest", "hacker", "backdoor"]
            # 审计日志
            detector = audit_user_activity(path, start_time, end_time)
            detector['mac']=data['macAddress']
            t = Thread(target=wrapper, args=('auditLog', detector, publisher, True))
            t.start()
        elif data['type'] == 'loginLog':
            from datetime import datetime, timedelta

            end_time = datetime.now()
            start_time = end_time - timedelta(hours=24)
            path = r"C:\Windows\System32\winevt\Logs\Security.evtx"
            suspicious_accounts = ["test1", "guest", "hacker", "backdoor"]
            # 审计日志
            detector = analyze_login_logs(path, suspicious_users=suspicious_accounts, start_time=start_time,
                       end_time=end_time)
            detector['mac']=data['macAddress']
            t = Thread(target=wrapper, args=('loginLog', detector, publisher, True))
            t.start()
        elif data['type'] == 'accountChangLog':
            from datetime import datetime, timedelta

            end_time = datetime.now()
            start_time = end_time - timedelta(hours=24)
            path = r"C:\Windows\System32\winevt\Logs\Security.evtx"
            suspicious_accounts = ["test1", "guest", "hacker", "backdoor"]
            # 审计日志
            detector = audit_account_changes(path, start_time, end_time)
            detector['mac']=data['macAddress']
            t = Thread(target=wrapper, args=('accountChangLog', detector, publisher, True))
            t.start()
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