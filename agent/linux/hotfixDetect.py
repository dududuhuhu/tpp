import subprocess
import json

class HotfixDetectLinux:
    def __init__(self, data):
        self.data = data

    def detect(self):
        """
        探测系统相关的补丁信息
        """
        print("开始探测系统相关补丁数据..............!")
        hotfix_list = []

        try:
            # 检测系统发行版并选择适合的命令
            if self.is_debian_based():
                hotfix_list = self.detect_with_dpkg()
            elif self.is_redhat_based():
                hotfix_list = self.detect_with_rpm()
            else:
                print("不支持的 Linux 发行版")
                return json.dumps([])

            result = json.dumps(hotfix_list, ensure_ascii=False)
            print("hotfixDetect:", result)
            print("探测系统相关补丁数据结束！")
            return result

        except Exception as e:
            print(f"探测补丁数据失败: {e}")
            return json.dumps([])

    def is_debian_based(self):
        """
        检测是否为 Debian 系列发行版
        """
        try:
            process = subprocess.run(['which', 'dpkg'], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
            return process.returncode == 0
        except Exception:
            return False

    def is_redhat_based(self):
        """
        检测是否为 Red Hat 系列发行版
        """
        try:
            process = subprocess.run(['which', 'rpm'], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
            return process.returncode == 0
        except Exception:
            return False

    def detect_with_dpkg(self):
        """
        使用 dpkg 查询系统相关的补丁信息（适用于 Debian 系列）
        """
        hotfix_list = []
        process = subprocess.Popen(['dpkg-query', '-W', '--showformat=${Package}\t${Version}\n'],
                                   stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        output, error = process.communicate()

        if process.returncode != 0:
            print(f"探测补丁数据失败: {error.decode('utf-8')}")
            return hotfix_list

        for line in output.decode('utf-8').splitlines():
            parts = line.split("\t")
            package_name = parts[0]
            version = parts[1]

            # 筛选系统相关的补丁（例如内核、glibc 等）
            if self.is_system_related_package(package_name):
                hotfix_list.append({
                    'mac': self.data.get('macAddress', ''),
                    'hotfixId': f'{package_name}-{version}',
                })
        return hotfix_list

    def detect_with_rpm(self):
        """
        使用 rpm 查询系统相关的补丁信息（适用于 Red Hat 系列）
        """
        hotfix_list = []
        process = subprocess.Popen(['rpm', '-qa', '--queryformat', '%{NAME}-%{VERSION}-%{RELEASE}\n'],
                                   stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        output, error = process.communicate()

        if process.returncode != 0:
            print(f"探测补丁数据失败: {error.decode('utf-8')}")
            return hotfix_list

        for line in output.decode('utf-8').splitlines():
            package_name = line.split("-")[0]

            # 筛选系统相关的补丁（例如内核、glibc 等）
            if self.is_system_related_package(package_name):
                hotfix_list.append({
                    'mac': self.data.get('macAddress', ''),
                    'hotfixId': line,
                })
        return hotfix_list

    def is_system_related_package(self, package_name):
        """
        判断是否为系统相关的补丁
        """
        # 系统相关的包名关键字列表
        system_related_keywords = ['linux', 'kernel', 'glibc', 'systemd', 'openssl', 'bash']
        return any(keyword in package_name for keyword in system_related_keywords)