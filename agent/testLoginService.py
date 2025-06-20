from service.loginService import LoginService
from mq import get_amqp_url
from service import *
from utils.crypto.src.asymmetric import SignKeyPair

SERVER_PUB_KEY='-----BEGIN PUBLIC KEY-----\nMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEhqAtQ5IZqhrUo/7W1K5NTZA6y5DUPVGMxdVtGi6Z4WXGv5NwAaz4T5pFs3rOBEz2S5dvKBn7PJ5NeSWd8mNijA==\n-----END PUBLIC KEY-----\n'

def main():
    s = SignKeyPair()
    s.generate()
    l = LoginService(server_pub_key=SERVER_PUB_KEY, amqp_url=get_amqp_url(HOST, PORT, USERNAME, PASSWORD, VHOST), \
                    login_exchange='sysinfo_exchange', login_routing_key='sysinfo', login_recv_exchange=f'agent_{MAC.replace(":", "")}_exchange', login_recv_queue=f'agent_{MAC.replace(":", "")}_queue', \
                    login_recv_routing_key=MAC.replace(":",""), heartbeat_exchange='sysinfo', heartbeat_queue='status_queue', heartbeat_routing_key='status', \
                    agent_key_pair=s)
    # l.start()
    # l.join()
    l.run()

if __name__ == '__main__':
    main()