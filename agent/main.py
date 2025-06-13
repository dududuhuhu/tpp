from service.configure import *
from mq.consumer import Consumer
from mq.publisher import Publisher
from service import *
from work.HeartCheck import HeartCheck
from system.SystemInfo import SystemInfo
from mq.RabbitMQ import RabbitMQ

def login():
    print("Agent 启动..................")
    print("Agent 开始获取系统信息..................")
    # 实例化这个类
    sys_info = SystemInfo()
    sys_info_data = sys_info.get_info()
    # 实例化MQ
    print("Agent 开始同步系统信息..................")
    rabbitmq = RabbitMQ()
    rabbitmq.produce_sysinfo(sys_info_data)
    print("Agent 同步系统信息结束..................")
    print("Agent 发送心跳的维持信息.................")
    # 怎么去获取MAC地址
    mac_address = sys_info.get_mac_address()
    # 实例化心跳检测的类
    heart_check = HeartCheck(mac_address,rabbitmq)
    # 启动多线程
    heart_check.start()

def startup():
    for routing_info in CONSUMER_ROUTING:
        routing_info.set_callback(routing_info.wrap_callback(default_consumer, default_publisher))
    default_consumer.add_queue_key_pairs(CONSUMER_ROUTING)
    default_publisher.add_queue_key_pairs(PUBLISHER_ROUTING)
    default_consumer.start()
    default_publisher.start()

def join():
    default_consumer.join()
    default_publisher.join()

def main():
    login()
    startup()
    join()

if __name__ == "__main__":
    main()