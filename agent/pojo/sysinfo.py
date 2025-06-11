import socket
import uuid
import platform
import subprocess
import math
import psutil
import json

INVALID_IP_ADDR = ""
INVALID_CPU_TYPE = "Unknown type"

class SysInfo:
    def __init__(self):
        self.__hostname = None
        self.__ip_address = None
        self.__mac_address = None
        self.__os_type = None
        self.__os_name = None
        self.__os_version = None
        self.__os_bit = None
        self.__cpu_type = None
        self.__ram = None
    
    def get_mac_address(self):
        return self.__mac_address

    def __get_hostname(self):
        """获取主机名"""
        self.__hostname = socket.gethostname()

    def __get_ip_address(self):
        """获取IP地址"""
        try:
            # 获取当前设备的实际 IP 地址，而不是通过主机名解析
            with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as s:
                s.connect(("114.114.114.114", 80))  # 使用外部地址进行连接以获取本地 IP
                self.__ip_address = s.getsockname()[0]
        except socket.error:
            self.__ip_address = INVALID_IP_ADDR

    def __get_mac_address(self):
        """获取MAC地址"""
        mac = uuid.getnode()
        self.__mac_address =  ':'.join(('%012X' % mac)[i:i+2] for i in range(0, 12, 2))

    def __get_sys_info(self):
        """使用platform获取操作系统相关信息"""

        self.__os_type = platform.system()
        self.__os_name = platform.platform()
        self.__os_version = platform.version()
        self.__os_bit = platform.architecture()[0]


    def __get_cpu_info(self):
        try:
            result = subprocess.run(
                ["cat", "/proc/cpuinfo"],
                text=True,
                capture_output=True,
                check=True
            )
            self.__cpu_type = INVALID_CPU_TYPE
            for line in result.stdout.splitlines():
                if line.startswith("model name"):
                    self.__cpu_type = line.split(":")[1].strip()
                    break
        except (subprocess.CalledProcessError, FileNotFoundError):
            pass

        self.__ram = math.ceil(psutil.virtual_memory().total / (1024 ** 3))  # 转换为GB
    
    def dump(self):
        self.__get_hostname()
        self.__get_ip_address()
        self.__get_mac_address()
        self.__get_sys_info()
        self.__get_cpu_info()
        return json.dumps({
            "hostName": self.__hostname,
            "ipAddress": self.__ip_address,
            "macAddress": self.__mac_address,
            "osType": self.__os_type,
            "osName": self.__os_name,
            "osVersion": self.__os_version,
            "osBit": self.__os_bit,
            "cpuType": self.__cpu_type,
            "ram": self.__ram
        })

