# coding=utf-8
import json
import threading
from impacket.examples.utils import parse_target
from impacket.smbconnection import SMBConnection
import subprocess


class PasswordDetect(threading.Thread):
    """
    弱密码探测的线程类
    """
    def __init__(self,mq,data):
        super().__init__()
        # 平台传递的指令
        self.__data=data

    def run(self):
        """
        线程运行方法
        :return:
        """
        self.__detect()
        # pass

    def __detect(self):
        """
        探测的方法
        :return:
        """
        self.__passwordDetect()

    def __list_windows_users(self):
        result = subprocess.run(["net", "user"], capture_output=True, text=True, shell=True)
        output = result.stdout
        users = []

        capture = False
        for line in output.splitlines():
            if "----" in line:
                capture = True
                continue
            if capture:
                if line.strip() == "":
                    break
                users.extend(line.strip().split())

        return users[:-1]

    def __smb_login(self):
        """
        遍历所有本地用户，尝试使用弱密码库进行 SMB 登录，返回扫描结果列表。
        """
        weak_passwords = [
            "123456", "123123", "admin", "password", "123", "111111", "root", "abc123"
        ]

        users = self.list_windows_users()
        results = []

        print("[*] 开始进行 SMB 弱口令扫描...")

        for user in users:
            user_result = {
                "username": user,
                "weak": False,
                "password": None
            }

            for pwd in weak_passwords:
                target = f"{user}:{pwd}@127.0.0.1"
                domain, username, password, address = parse_target(target)
                try:
                    smbClient = SMBConnection(address, address, sess_port=445)
                    smbClient.login(username, password, domain, '', '')
                    print(f"[+] 用户 {username} 存在弱口令：{password}")
                    user_result["weak"] = True
                    user_result["password"] = password
                    smbClient.logoff()
                    break  # 找到一个弱口令就不继续测这个用户了
                except Exception:
                    continue

            if not user_result["weak"]:
                print(f"[-] 用户 {user} 不存在已知弱口令")

            results.append(user_result)

        return results

    def __passwordDetect(self):
        ###补丁发现
        print("开始探测弱密码数据..............!")
        data=self.__smb_login()
        password_data=json.dumps(data)
        return password_data
