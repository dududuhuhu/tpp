from utils.crypto.src.asymmetric import SignKeyPair
import pika
from system.SystemInfo import SystemInfo
import json
from mq.service import Service
from utils import logger
from mq import extract_public_key
from service import MAC
from time import sleep

from utils.crypto.src import translate_bytes_to_str, translate_str_to_bytes

LOGIN_SUCCESS = 1
LOGIN_FAIL = 0

PUBLISH_INTERVAL = 2

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

        # self._sysinfo = SystemInfo()
        # self._sysinfo.get_info()
        # self._mac = self._sysinfo.get_mac_address()
        self._mac = MAC

        self._login_exchange = login_exchange
        self._login_routing_key = login_routing_key
        self._login_recv_exchange = login_recv_exchange
        self._login_recv_queue = login_recv_queue
        self._login_recv_routing_key = login_recv_routing_key
        self._heartbeat_exchange = heartbeat_exchange
        self._heartbeat_queue = heartbeat_queue
        self._heartbeat_routing_key = heartbeat_routing_key
        self._amqp_url = amqp_url

        self._heartbeat_data = json.dumps({
            'macAddress':self._mac,
            'status':1,
        })

        self._login()
        super().__init__(self._login_exchange, self._amqp_url)
        self._queue_key_pairs = {
            self._heartbeat_queue: self._heartbeat_routing_key
        }
        self.add_queue_key_pairs(self._queue_key_pairs)
    
    def _login(self):
        sysinfo = SystemInfo()
        sysinfo_data = sysinfo.get_info()
        # sysinfo_data = json.dumps({
        #     "host_name":"boat",
        #     "ip_address":"127.0.0.1",
        #     "mac_address":MAC,
        #     "os_type":"Linux",
        #     "os_name":"Ubuntu",
        #     "os_version":"0000",
        #     "os_bit":"64bit",
        #     "cpu_name":"cpuname",
        #     "ram":16,
        # })
        login_data_d:dict = json.loads(sysinfo_data)
        login_data_d['pub'] = extract_public_key(self._key_pair.get_pem_pub())
        login_message = json.dumps(login_data_d)

        sig = self._key_pair.sign((self._mac + login_message).encode('utf-8'))
        data = {
            'mac':self._mac,
            'message':login_message,
            'sig':translate_bytes_to_str(sig),
        }
        body = json.dumps(data)

        while True:
            print("execute")
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
                recv_body = None
                while recv_body is None:
                    sleep(1)
                    method_frame, header_frame, recv_body = channel.basic_get(
                        queue=self._login_recv_queue,
                        auto_ack=True
                    )

                response:dict = json.loads(recv_body)
                _message = response.get('message')
                _sig = response.get('sig')
                if _message is not None and _sig is not None \
                    and self._verifier.verify(
                            data=_message.encode('utf-8'), 
                            signature=translate_str_to_bytes(_sig)
                        ):
                    status = json.loads(_message)['status']
                    if status == LOGIN_SUCCESS:
                        return
                    else:
                        print("login fail, retrying...")
            except Exception as e:
                print(f"Error consuming login response: {e}")
                continue

    def _app_on_bindok(self, frame, userdata):
        logger.info("LoginService bind ok!")
        self._start_publishing()
    
    def _start_publishing(self):
        self._enable_delivery_confirmations()
        self._schedule_next_message()
    
    def _enable_delivery_confirmations(self):
        self._channel.confirm_delivery(self._on_delivery_confirmation)
    
    def _on_delivery_confirmation(self, method_frame):
        pass

    def _schedule_next_message(self):
        self._connection.ioloop.call_later(PUBLISH_INTERVAL, self._publish_message)

    def _publish_message(self):
        # Maintain heartbeat
        if self._channel is None or not self._channel.is_open:
            return
        properties = pika.BasicProperties(content_type='application/json')
        sig_s = translate_bytes_to_str(self._key_pair.sign((self._mac + self._heartbeat_data).encode('utf-8')))
        data = {
            'mac':self._mac,
            'message':self._heartbeat_data,
            'sig':sig_s,
        }
        self._channel.basic_publish(self._login_exchange, self._heartbeat_routing_key, json.dumps(data, ensure_ascii=False), properties=properties)
        self._schedule_next_message()
        

