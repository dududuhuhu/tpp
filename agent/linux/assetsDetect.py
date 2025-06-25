import json
import nmap
import psutil
import subprocess

class AcountDetectorLinux:
    def __init__(self, data):
        self.data = data  # 保存外部传入的数据

    def detect(self):
        """
        探测账号的方法
        """
        print("开始探测账号数据.............!")
        account_list = []

        try:
            with open("/etc/passwd", "r") as f:
                for line in f:
                    parts = line.strip().split(":")
                    user_dict = {
                        "mac": self.data.get("macAddress", ""),
                        "name": parts[0],  # 用户名
                        # "uid": parts[2],   # 用户ID
                        # "gid": parts[3],   # 组ID
                        # "home": parts[5],  # 主目录
                        # "shell": parts[6]  # 登录 shell
                    }
                    account_list.append(user_dict)
        except Exception as e:
            print("读取账号数据失败:", e)

        account_data = json.dumps(account_list, ensure_ascii=False)
        print(account_data)
        print("探测账号数据结束")
        return account_data

class ServiceDetectorLinux:
    def __init__(self, data):
        self.data = data  # 保存外部传入的数据

    def detect(self):
        """
        探测服务信息
        """
        print("开始探测服务数据.............!")
        nm = nmap.PortScanner()
        nm.scan('127.0.0.1', arguments='-sTV')

        state = nm.all_hosts()
        res_list = []

        if state:
            for host in state:
                for proto in nm[host].all_protocols():
                    lport = nm[host][proto].keys()
                    for port in lport:
                        nmap_res = {
                            'mac': self.data.get('macAddress', ''),
                            'protocol': proto,
                            'port': port,
                            'state': nm[host][proto][port]['state'],
                            'name': nm[host][proto][port]['name'],
                            'product': nm[host][proto][port]['product'],
                            'version': nm[host][proto][port]['version'],
                            'extrainfo': nm[host][proto][port]['extrainfo'],
                        }
                        res_list.append(nmap_res)

        res_json = json.dumps(res_list, ensure_ascii=False)
        print(res_json)
        print("服务数据探测结束！")
        return res_json

class ProcessDetectorLinux:
    def __init__(self, data):
        self.data = data  # 保存外部传入的数据

    def detect(self):
        """
        探测进程信息
        """
        print('开始探测进程数据......!')
        process_list = []

        try:
            for proc in psutil.process_iter(attrs=["pid", "ppid", "name", "cmdline", "cpu_percent"]):
                process_info = {
                    'mac': self.data.get('macAddress', ''),
                    'pid': proc.info["pid"],
                    'ppid': proc.info["ppid"],
                    'name': proc.info["name"],
                    'cmd': " ".join(proc.info["cmdline"]) if proc.info["cmdline"] else "",
                    'cpu_percent': proc.info["cpu_percent"]
                }
                process_list.append(process_info)
        except Exception as e:
            print("探测进程数据失败:", e)

        process_data = json.dumps(process_list, ensure_ascii=False)
        print(process_data)
        print('进程数据探测结束！')
        return process_data

class AppDetectorLinux:
    def __init__(self, data):
        self.data = data  # 保存外部传入的数据

    def detect(self):
        """
        探测已安装应用信息
        """
        print('开始探测app数据......!')
        software_list = []

        try:
            # 使用 dpkg-query 获取已安装软件列表（适用于 Debian 系列）
            process = subprocess.Popen(['dpkg-query', '-W', '--showformat=${Package}\t${Version}\n'],
                                       stdout=subprocess.PIPE, stderr=subprocess.PIPE)
            output, _ = process.communicate()
            for line in output.decode('utf-8').splitlines():
                parts = line.split("\t")
                software = {
                    'mac': self.data.get('macAddress', ''),
                    'name': parts[0],  # 软件名称
                    'version': parts[1]  # 软件版本
                }
                software_list.append(software)
        except Exception as e:
            print("探测app数据失败:", e)

        app_data = json.dumps(software_list, ensure_ascii=False)
        print(app_data)
        print("APP数据探测结束！")
        return app_data
