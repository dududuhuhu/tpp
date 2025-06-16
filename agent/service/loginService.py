from utils.crypto.src.asymmetric import SignKeyPair
import pika
from system.SystemInfo import SystemInfo
import json
import base64
from mq.service import Service
from utils import logger

def translate_bytes_to_str(data:bytes) -> str:
    return base64.b64encode(data).decode('utf-8')

def translate_str_to_bytes(data:str) -> bytes:
    return base64.b64decode(data.encode('utf-8'))

LOGIN_SUCCESS = 1
LOGIN_FAIL = 0

class LoginService(Service):
    def __init__(self, server_pub_key:str, \
                amqp_url:str, \
                login_exchange:str, login_routing_key:str, \
                login_recv_exchange:str, login_recv_queue:str, login_recv_routing_key:str, \
                heartbeat_exchange:str, heartbeat_queue:str, heartbeat_routing_key:str, \
                agent_key_pair:SignKeyPair=None):
        if agent_key_pair is None:
            agent_key_pair = SignKeyPair()
            agent_key_pair.generate()
        self._key_pair = agent_key_pair
        self._verifier = SignKeyPair()
        self._verifier.load_pub(server_pub_key)

        self._sysinfo = SystemInfo()
        self._mac = self._sysinfo.get_mac_address()

        self._login_exchange = login_exchange
        self._login_routing_key = login_routing_key
        self._login_recv_exchange = login_recv_exchange
        self._login_recv_queue = login_recv_queue
        self._login_recv_routing_key = login_recv_routing_key
        self._heartbeat_exchange = heartbeat_exchange
        self._heartbeat_queue = heartbeat_queue
        self._heartbeat_routing_key = heartbeat_routing_key
        self._amqp_url = amqp_url

        self._login()
        super().__init__(self._login_exchange, self._amqp_url)
        self._queue_key_pairs = {
            self._heartbeat_queue: self._heartbeat_routing_key
        }
        self.add_queue_key_pairs(self._queue_key_pairs)
    
    def _login(self):
        sysinfo = SystemInfo()
        sysinfo_data = sysinfo.get_info()
        sig = self._key_pair.sign(translate_str_to_bytes(sysinfo_data))
        data = {
            'mac':sysinfo.get_mac_address(),
            'message':sysinfo_data,
            'sig':translate_bytes_to_str(sig),
        }
        body = json.dumps(data)

        while True:
            connection = pika.BlockingConnection(pika.URLParameters(self._amqp_url))
            channel = connection.channel()
            channel.basic_publish(
                exchange=self._login_exchange,
                routing_key=self._login_routing_key,
                body=body,
                properties=pika.BasicProperties(
                    content_type='application/json',
                ),
            )
            try:
                method_frame, header_frame, body = channel.basic_get(
                    queue=self._login_recv_queue,
                    auto_ack=True
                )
                if body:
                    response:dict = json.loads(body)
                    _message = response.get('message')
                    _sig = response.get('sig')
                    if _message is not None and _sig is not None \
                        and self._verifier.verify(
                                data=translate_str_to_bytes(_message), 
                                signature=translate_str_to_bytes(_sig)
                            ):
                        status = json.loads(_message)['status']
                        if status == LOGIN_SUCCESS:
                            return
                else:
                    print("No response, retrying...")
            except Exception as e:
                print(f"Error consuming login response: {e}")
                continue

    def _app_on_bindok(self, frame, userdata):
        logger.info("LoginService bind ok!")
        self._start_publishing(self)
    
    def _start_publishing(self):
        self._enable_delivery_confirmations()
        self._schedule_next_message()
    
    def _enable_delivery_confirmations(self):
        self._channel.confirm_delivery(self._on_delivery_confirmation)
    
    def _on_delivery_confirmation(self, method_frame):
        pass

    def _publish_message(self):
        TODO
        # if self._channel is None or not self._channel.is_open:
        #     return

        # properties = pika.BasicProperties(content_type='application/json')
        # message = {
        #     "macAddress":self.
        # }
        

