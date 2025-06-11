from threading import Thread
from mq.RabbitMQ import RabbitMQ
import json
from time import sleep
import inspect

HEART_BEAT_INTERVAL = 3 # seconds

class UpdateStatus(Thread):
    def __init__(self, mac_address:str, mq:RabbitMQ):
        super().__init__()
        self.__mac_address:str = mac_address
        self.__mq:RabbitMQ = mq
        self.__data:str = None
        self.__generate_data()

    def __generate_data(self) -> str:
        self.__data = json.dumps({
            'macAddress' : self.__mac_address,
        })
        return self.__data

    def run(self):
        while True:
            try:
                self.__mq.publish(self.__data, routing_key='status')
                sleep(HEART_BEAT_INTERVAL)
            except Exception as e:
                print(f"Exception occurs: {e} . file: {__file__} line: {inspect.currentframe().f_lineno}")
