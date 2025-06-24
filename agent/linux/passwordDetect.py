import crypt
import json
import requests


class WeakPasswordDetect:
    def __init__(self, data):
        self.mac = data.get("macAddress", "")
        self.weak_passwords = self.load_from_backend("http://localhost:8080/rule/weakPassword")

    def load_from_backend(self, url):
        """
        从后端接口获取弱口令规则列表
        """
        try:
            response = requests.get(url, timeout=5)
            response.raise_for_status()
            data = response.json()
            print(f"已获得{len(data)}条弱口令")
            return data if isinstance(data, list) else []
        except Exception as e:
            print(f"获取弱口令列表失败：{e}")
            return []

    def read_shadow_file(self):
        """
        读取 /etc/shadow 文件
        """
        shadow_data = {}
        try:
            with open("/etc/shadow", "r") as f:
                for line in f:
                    parts = line.strip().split(":")
                    username = parts[0]
                    password_hash = parts[1]
                    shadow_data[username] = password_hash
        except PermissionError:
            print("需要超级用户权限才能访问 /etc/shadow 文件")
        except Exception as e:
            print(f"读取 /etc/shadow 文件失败：{e}")
        return shadow_data

    def detect(self):
        """
        检查弱密码
        """
        shadow_data = self.read_shadow_file()
        results = []

        for username, password_hash in shadow_data.items():
            if password_hash in ["*", "!", ""]:
                # 跳过无效或锁定的账户
                continue

            for weak_password in self.weak_passwords:
                # 使用 crypt 模块生成哈希值
                generated_hash = crypt.crypt(weak_password, password_hash)
                if generated_hash == password_hash:
                    print(f"[+] 用户 {username} 存在弱口令")
                    results.append({
                        "mac": self.mac,
                        "username": username,
                        "weak": True,
                        "password": weak_password
                    })
                    break

        data = json.dumps(results, ensure_ascii=False)
        print(data)
        print("弱口令检查结束！")
        return data