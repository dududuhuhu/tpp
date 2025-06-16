import json
import requests
from impacket.examples.utils import parse_target
from impacket.smbconnection import SMBConnection
import subprocess


class SMBWeakPasswordScanner:
    def __init__(self, data):
        print(data)
        # 初始化时从 data 字典中获取目标主机 MAC 和 IP
        self.mac = data['macAddress']
        self.target_ip = data['ipAddress']
        # 从后端接口获取弱口令列表
        self.weak_passwords = self.load_from_backend("http://localhost:8080/rule/weakPassword")

    def load_from_backend(self, url):
        """
        从后端接口获取弱口令规则列表
        """
        try:
            response = requests.get(url, timeout=5)  # 设置请求超时时间
            response.raise_for_status()  # 如果状态码不是 200，将抛出异常
            data = response.json()  # 解析 JSON 响应，应为一个字符串列表
            print(data)
            print(f"已获得{len(data)}条弱口令")
            return data if isinstance(data, list) else []  # 确保返回是列表类型
        except Exception as e:
            print(f"获取弱口令列表失败：{e}")
            return []

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
