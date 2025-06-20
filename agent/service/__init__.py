from mq.consumer import Consumer
from mq.publisher import Publisher
from mq import get_amqp_url
from service.configure import *
from utils.crypto.src.asymmetric import SignKeyPair
from service.configure import SERVER_PEM_PUB
from user.userConfig import USER_PEM_PRI

all = ['default_consumer', 'default_publisher', 'user_signer', 'server_verifier']
server_verifier = SignKeyPair()
server_verifier.load_pub(SERVER_PEM_PUB)
user_signer = SignKeyPair()
user_signer.load_pri(USER_PEM_PRI)
url = get_amqp_url(HOST, PORT, USERNAME, PASSWORD, VHOST)
default_consumer = Consumer(exchange=CONSUMER_EXCHANGE_NAME, amqp_url=url, verify_key_pair=server_verifier)
default_publisher = Publisher(exchange=PUBLISHER_EXCHANGE_NAME, amqp_url=url, sign_key_pair=user_signer, mac=MAC)


