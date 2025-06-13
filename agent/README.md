### 运行前修改

- agent\service\configure.py

​	改成自己的mq设置

HOST = '192.168.192.128'
PORT = 4568
USERNAME = 'admin'
PASSWORD = '20250606'
VHOST = 'my_vhost'


- agent\mq\RabbitMQ.py

​	改成自己的设置

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

### 运行

直接运行agent\main.py