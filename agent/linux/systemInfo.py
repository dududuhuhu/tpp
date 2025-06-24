import json
import math
import socket
import uuid
import platform
import psutil
import subprocess


class SystemInfoLinux(object):
    """
    封装一个类：用于获得主机的基本信息（适用于 Linux 平台）
        1. 主机的名字
        2. 主机的 IP 地址
        3. 主机的操作系统
        4. 主机的 MAC 地址
        5. 主机的具体系统类型
        6. 主机的具体操作系统版本号
        7. 主机的 CPU 信息
        8. 主机的内存大小
        9. 主机的操作系统位数
    """
    def __init__(self):
        self.__host_name = ""
        self.__ip_address = ""
        self.__mac_address = ""
        self.__os_type = ""
        self.__os_name = ""
        self.__os_version = ""
        self.__os_bit = ""
        self.__cpu_name = ""
        self.__ram = ""

    def __get_host_name(self):
        self.__host_name = socket.gethostname()

    def __get_ip(self):
        """
        获取 IP 地址
        """
        s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        try:
            s.connect(("8.8.8.8", 80))  # 使用 Google 的公共 DNS 地址
            self.__ip_address = s.getsockname()[0]
        finally:
            s.close()

    def __get_mac(self):
        """
        获取 MAC 地址
        """
        mac = ':'.join(("%012X" % uuid.getnode())[i:i + 2] for i in range(0, 12, 2))
        self.__mac_address = mac

    def __get_os_info(self):
        """
        获取操作系统相关信息
        """
        self.__os_type = platform.system()
        self.__os_name = platform.linux_distribution()[0] if hasattr(platform, 'linux_distribution') else "Linux"
        self.__os_version = platform.version()
        self.__os_bit = platform.architecture()[0]

    def __get_cpu_info(self):
        """
        获取 CPU 信息
        """
        try:
            # 使用 lscpu 命令获取 CPU 名称
            process = subprocess.Popen(['lscpu'], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
            output, _ = process.communicate()
            for line in output.decode('utf-8').split('\n'):
                if "Model name" in line:
                    self.__cpu_name = line.split(":")[1].strip()
                    break
        except Exception:
            self.__cpu_name = "Unknown"

    def __get_ram_info(self):
        """
        获取内存大小
        """
        self.__ram = math.ceil(psutil.virtual_memory().total / 1024 / 1024 / 1024)

    def get_info(self):
        """
        获取基本信息的方法
        """
        self.__get_host_name()
        self.__get_ip()
        self.__get_mac()
        self.__get_os_info()
        self.__get_cpu_info()
        self.__get_ram_info()

        # 封装成字典
        info = {
            "host_name": self.__host_name,
            "ip_address": self.__ip_address,
            "mac_address": self.__mac_address,
            "os_type": self.__os_type,
            "os_name": self.__os_name,
            "os_version": self.__os_version,
            "os_bit": self.__os_bit,
            "cpu_name": self.__cpu_name,
            "ram": self.__ram
        }
        # 转换成 JSON 数据
        return json.dumps(info)

    # mac_address 的 getter 方法
    def get_mac_address(self):
        return self.__mac_address