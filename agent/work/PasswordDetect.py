import json

import requests
from impacket.examples.utils import parse_target
from impacket.smbconnection import SMBConnection
import subprocess


class SMBWeakPasswordScanner:
    def __init__(self, data):
        self.mac=data['macAddress']
        self.target_ip=data['ipAddress']
        self.weak_passwords = self.load_from_backend("http://localhost:8080/rule/weakPassword")

    def load_from_backend(self, url):
        try:
            response = requests.get(url, timeout=5)
            response.raise_for_status()
            data = response.json()  # 应该是一个字符串列表
            print(data)
            print(f"已获得{len(data)}条弱口令")
            return data if isinstance(data, list) else []
        except Exception as e:
            print(f"获取弱口令列表失败：{e}")
            return []

    def list_local_users(self):
        """
        获取本地 Windows 系统中的所有用户（非空行、非标题）
        """
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

        return users[:-1]  # 去掉命令提示尾部内容

    def detect(self):
        """
        执行弱口令扫描，返回结果列表
        """
        users = self.list_local_users()
        results = []
        print("开始弱口令探测...................!")
        print("[*] 开始进行 SMB 弱口令扫描...")

        for user in users:
            user_result = {
                "mac":self.mac,
                "username": user,
                "weak": False,
                "password": None
            }

            for pwd in self.weak_passwords:
                target = f"{user}:{pwd}@{self.target_ip}"
                domain, username, password, address = parse_target(target)

                try:
                    smbClient = SMBConnection(address, address, sess_port=445)
                    smbClient.login(username, password, domain, '', '')
                    print(f"[+] 用户 {username} 存在弱口令：{password}")
                    user_result["weak"] = True
                    user_result["password"] = password
                    smbClient.logoff()
                    break
                except Exception:
                    continue

            if not user_result["weak"]:
                print(f"[-] 用户 {user} 不存在已知弱口令")

            results.append(user_result)
        data=json.dumps(results)
        print("探测弱口令结束！")
        return data

