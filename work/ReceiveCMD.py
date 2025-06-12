# coding=utf-8
import threading


class ReceiveCMD(threading.Thread):
    """
    专门用于接收平台发送指令的类
    """
    def __init__(self, mq , mac_address):
        super().__init__()
        # mq对象
        self.__mq=mq
        # mac地址，用来作为队列名称
        self.__mac_address = mac_address

    def run(self):
        # 组装队列的名字，格式：agent_[mac_address去除：]_queue
        queue_name = "agent_" + self.__mac_address.replace(":", "") + "_queue"
        print("开始监听队列：" + queue_name)
        # 获取队列
        self.__mq.consume_queue(queue_name)

