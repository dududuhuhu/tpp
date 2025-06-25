from mq.consumer import Consumer

import json
from db.tpp import update_log_rules
import uuid
from utils.crypto.src import translate_str_to_bytes

def save_log_rule(consumer:Consumer, publisher, channel, basic_deliver, properties, body):
    try:
        print(body.decode('utf-8'))
        _body = json.loads(body.decode('utf-8'))
        if (not consumer._verify_key_pair.verify(_body['message'].encode('utf-8'), translate_str_to_bytes(_body['sig']))):
            return
        data = json.loads(_body['message'])
        print(f"Received message: {data}")
        # 在这里处理日志规则保存逻辑
        rules = []
        for rule in data:
            rules.append((rule['id'], rule['platform'], rule['eventId'], rule['riskLevel'], rule['description']))
        update_log_rules(rules)
    except Exception as e:
        print(f"Error processing message: {e}")
        return
    finally:
        consumer.acknowledge_message(basic_deliver.delivery_tag)
    
def request_log_rule():
    return json.dumps({
        'mac':':'.join(("%012X" % uuid.getnode())[i:i + 2] for i in range(0, 12, 2)),
    })
            

