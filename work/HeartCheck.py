# codeing=utf-8
import json
import threading
from time import sleep

class HeartCheck(threading.Thread):
    """
    心跳维持类
    """
    def __init__(self,mac_address,mq):
        super().__init__()
        # mac地址
        self.__mac_address = mac_address
        # 消息队列对象
        self.__mq = mq

    def run(self):
        """
        心跳发送的逻辑代码
        :return:
        """

        # 组装一个数据
        status_data = {
            "macAddress": self.__mac_address,
            "status": 1
        }
        # 转换成JSON
        status_data = json.dumps(status_data)

        while True:
            try:
                # 生产消息
                self.__mq.produce_status_info(status_data)
                sleep(3)
            except Exception as e:
                # 捕获异常并打印错误信息
                print(f"心跳线程发生异常: {e}")
                # 可以在这里添加一些延迟，避免频繁重试
                sleep(1)




