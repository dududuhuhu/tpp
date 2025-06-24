from mq.consumer import Consumer

import json
from db.tpp import update_log_rules
import uuid

def save_log_rule(consumer:Consumer, publisher, channel, basic_deliver, properties, body):
    try:
        print(body.decode('utf-8'))
        _body = json.loads(body.decode('utf-8'))
        if (not consumer._verify_key_pair.verify(_body['message'].encode('utf-8'), _body['sig'].encode('utf-8'))):
            return
        data = json.loads(_body['message'])
        print(f"Received message: {data}")
        # 在这里处理日志规则保存逻辑
        rules = []
        for rule in data:
            rules.append(rule)
        update_log_rules(rules)
    except Exception as e:
        print(f"Error processing message: {e}")
        return
    
def request_log_rule():
    return json.dumps({
        'mac':':'.join(("%012X" % uuid.getnode())[i:i + 2] for i in range(0, 12, 2)),
    })
            

