import json

import pika

from work.AssetsDetect import AssetsDetect


class RabbitMQ:
    def __init__(self):
        self.__host = '192.168.192.128'
        # self.__host = '10.100.1.136'
        self.__port = '4568'
        self.__user = 'admin'
        self.__password = '20250606'
        # self.__password = '20240606'
        self.__virtual_host = 'my_vhost'
        self.__connection = ''
        self.__channel = ''
        # 初始化连接
        self.__get_connection()


    def __get_connection(self):
        """
       获取连接对象
       :return:
        """
        # 获取认证对象
        credentials = pika.PlainCredentials(self.__user, self.__password)
        # 获取连接
        self.__connection = pika.BlockingConnection(
            pika.ConnectionParameters(host=self.__host, port=self.__port,
                                      virtual_host=self.__virtual_host,
                                      credentials=credentials))
        # 获取通道
        self.__channel = self.__connection.channel()


    def __my_producer(self, exchange, routing_key, data):
        """
        生产者
        :param routing_key: 路由键
        :param exchange: 交换机
        :param data: 数据
        :return:
        """
        self.__channel.basic_publish(exchange=exchange, routing_key=routing_key,
                                     body=data)


    def __process_message(self, ch, method, properties, message):
        """
        处理消息
        :param ch:
        :param method:
        :param properties:
        :param message:
        :return:
        """
        # JSON字符串转换成字典
        data = json.loads(message)
        print(data)
        # 判断类型
        if data['type']=='assets':
            # 资产探测
            assets_detect = AssetsDetect(self,data)
            assets_detect.start()
            pass

    def consume_queue(self,queue_name):
        """
        消费队列数据
        :param queue_name:
        :return:
        """
        # 消费队列
        print("开始消费队列："+queue_name)
        self.__channel.basic_consume(queue=queue_name,
                                     on_message_callback=self.__process_message, auto_ack=True)
        # 开始监听
        self.__channel.start_consuming()
    def produce_sysinfo(self, data):
        """
        生产系统信息
        :return:
        """
        exchange = 'sysinfo_exchange'
        routing_key = 'sysinfo'
        # 发送数据
        self.__my_producer(exchange, routing_key, data)

    def produce_status_info(self,data):
        """
        生产状态维持信息
        :param data:
        data的格式：
        {'mac_address':'xxxxxxxxxx',
         'status':1}
        :return:
        """
        exchange = 'sysinfo_exchange'
        routing_key = 'status'
        # 发送数据
        self.__my_producer(exchange, routing_key, data)

    def produce_account_info(self,data):
        """
        生产账户信息
        :param data:
        :return:
        """
        exchange = 'sysinfo_exchange'
        routing_key = 'account'
        # 发送数据
        self.__my_producer(exchange, routing_key, data)

    def produce_service_info(self,data):
        """
        生产服务信息
        :param data:
        :return:
        """
        exchange = 'sysinfo_exchange'
        routing_key = 'service'
        # 发送数据
        self.__my_producer(exchange, routing_key, data)

    def produce_process_info(self,data):
        """
        生产进程信息
        :param data:
        :return:
        """
        exchange = 'sysinfo_exchange'
        routing_key = 'process'
        # 发送数据
        self.__my_producer(exchange, routing_key, data)

    def prodocu_app_info(self,data):
        """
        生产应用信息
        :param data:
        :return:
        """
        exchange = 'sysinfo_exchange'
        routing_key = 'app'
        # 发送数据
        self.__my_producer(exchange, routing_key, data)
