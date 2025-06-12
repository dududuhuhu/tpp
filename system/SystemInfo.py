# 创建一个类
import json
import math
import socket
import subprocess
import uuid
import platform

import psutil


class SystemInfo(object):
    """
    封装一个类：只要用于获得主机的基本信息
        1.主机的名字
        2.主机的IP地址
        3.主机的操作系统
        4.主机的MAC地址
        5.主机的具体系统类型
        6.主机的具体操作系统版本号
        7.主机的CPU信息
        8.主机的内存大小
        9.主机的操作系统位数
    """
    def __init__(self):
        # 主机的名字
        self.__host_name = ""
        # 主机的IP地址
        self.__ip_address = ""
        #主机的MAC地址
        self.__mac_address = ""
        self.__os_type = ""
        self.__os_name = ""
        self.__os_version = ""
        self.__os_bit = ""
        self.__cpu_name = ""
        self.__ram = ""

    def __get_host_name(self):
        self.__host_name=socket.gethostname()

    def __get_ip(self):
        """
            获取IP地址
            :return:
            """
        s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        s.connect(("114.114.114.114", 80))
        ip_address = s.getsockname()[0]
        s.close()
        self.__ip_address = ip_address

    def __get_mac(self):
        """
            获取MAC地址: 这个是逻辑MAC，但也是唯一的，只是做一个标识
            :return:
            """
        mac = ':'.join(("%012X" % uuid.getnode())[i:i + 2] for i in range(0, 12, 2))
        self.__mac_address = mac


    def __get_os_type(self):
        """
            获取操作系统相关信息
            :return:
            """
        self.__os_type = platform.system()
        # 这个地方获取的可能和操作系统里面显示的不一样，显示的是出厂的操作系统
        # self.__os_name = platform.platform()
        # 这个可以获取实际的操作系统版本，但是不跨平台
        self.__os_name = self.__get_win_os_name()
        self.__os_version = platform.version()
        self.__os_bit = platform.architecture()[0]

    def __get_win_os_name(self):
        """
        获取win的实际操作系统版本
        :return:
        """
        # 执行wmic命令获取操作系统名称
        process = subprocess.Popen(['wmic', 'os', 'get', 'Caption'],
                                   stdout=subprocess.PIPE)
        output, _ = process.communicate()
        # 解析输出结果，获取操作系统名称
        return output.strip().decode('GBK').split('\n')[1]

    def __get_win_cpu_name(self):
        """
        获取win cpu 的名字
        :return:
        """
        process = subprocess.Popen(['wmic', 'cpu', 'get', 'name'],
                                   stdout=subprocess.PIPE)
        output, _ = process.communicate()
        return output.strip().decode('utf-8').split('\n')[1]

    def __get_cpu_ram_info(self):
        """
            获取cpu,ram相关信息
            :return:
            """
        self.__cpu_name = self.__get_win_cpu_name()
        self.__ram = math.ceil(psutil.virtual_memory().total / 1024 / 1024 / 1024)

    def get_info(self):
        """
        获取基本信息的方法
        :return:
        """
        self.__get_host_name()
        self.__get_ip()
        self.__get_mac()
        self.__get_os_type()
        self.__get_cpu_ram_info()


        # 我们应该返回一个什么样的数据给Java端呢？ JSON：key=>value
        # 所以我们要将这些信息封装成一个字典
        info={
            "host_name":self.__host_name,
            "ip_address":self.__ip_address,
            "mac_address":self.__mac_address,
            "os_type":self.__os_type,
            "os_name":self.__os_name,
            "os_version":self.__os_version,
            "os_bit":self.__os_bit,
            "cpu_name":self.__cpu_name,
            "ram":self.__ram
        }
        # 将info转换成JSON数据
        return json.dumps(info)

    # mac_address的getter方法
    def get_mac_address(self):
        return self.__mac_address