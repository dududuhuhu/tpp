from system.SystemInfo import SystemInfo
from mq.RabbitMQ import RabbitMQ
from work.HeartCheck import HeartCheck
from work.ReceiveCMD import ReceiveCMD

if __name__ == '__main__':
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

    # 消费消息也应该搞一个单独的线程：ReceiveCMD
    print("Agent 准备消息接收消息..................")
    rabbitmq=RabbitMQ()
    receive_cmd=ReceiveCMD(rabbitmq,mac_address)
    receive_cmd.start()


