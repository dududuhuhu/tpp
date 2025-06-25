import json
import requests
from impacket.examples.utils import parse_target
from impacket.smbconnection import SMBConnection
import subprocess


class SMBWeakPasswordScanner:
    def __init__(self, data):
        print(data)
        self.mac = data['macAddress']
        self.target_ip = data['ipAddress']
        self.weak_passwords = data.get('weakPasswords', [])  # ← 从参数里取弱密码列表
        print(self.weak_passwords)
        print(f"已获得{len(self.weak_passwords)}条弱口令")

    def list_local_users(self):
        """
        获取本地 Windows 系统中的所有用户（排除空行和标题）
        """
        # 运行 net user 命令列出用户
        result = subprocess.run(["net", "user"], capture_output=True, text=True, shell=True)
        output = result.stdout
        users = []

        capture = False  # 是否开始采集用户行
        for line in output.splitlines():
            if "----" in line:
                capture = True
                continue
            if capture:
                if line.strip() == "":
                    break
                users.extend(line.strip().split())  # 将每行的用户名添加到列表中

        return users[:-1]  # 去掉最后一行（通常为命令提示符或非用户内容）

    def detect(self):
        users = self.list_local_users()
        results = []
        print("开始弱口令探测...................!")
        print("[*] 开始进行 SMB 弱口令扫描...")

        for user in users:
            for pwd in self.weak_passwords:
                target = f"{user}:{pwd}@{self.target_ip}"
                domain, username, password, address = parse_target(target)

                try:
                    smbClient = SMBConnection(address, address, sess_port=445)
                    smbClient.login(username, password, domain, '', '')
                    print(f"[+] 用户 {username} 存在弱口令")
                    results.append({
                        "mac": self.mac,
                        "username": username,
                        "weak": True,
                        "password": password
                    })
                    smbClient.logoff()
                    break  # 找到一个弱口令就跳出当前用户密码循环
                except Exception:
                    continue
            else:
                print(f"[-] 用户 {user} 不存在已知弱口令")

        data = json.dumps(results)
        print(data)
        print("探测弱口令结束！")
        return data
