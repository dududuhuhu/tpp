import datetime
import json
import ntplib
import winreg
import subprocess

class SystemRiskDetect:
    def __init__(self,data):
        # 规则列表定义
        self.rules = [
            {
                "id": 1,
                "name": "服务器时间校验",
                "description": "服务器时间与NTP偏差大于5分钟",
                "check_function": self.check_time_sync
            },
            {
                "id": 3,
                "name": "网卡混杂模式",
                "description": "网卡处于混杂模式，可能用于嗅探网络数据",
                "check_function": self.check_promiscuous_mode
            }
        ]

    def check_time_sync(self, threshold_minutes=5):
        try:
            client = ntplib.NTPClient()
            response = client.request('pool.ntp.org')
            ntp_time = datetime.datetime.utcfromtimestamp(response.tx_time)
            local_time = datetime.datetime.utcnow()
            delta = abs((ntp_time - local_time).total_seconds() / 60)
            return delta <= threshold_minutes, f"NTP差值: {delta:.2f}分钟"
        except Exception as e:
            return False, f"NTP同步检查失败: {e}"

    def check_ip_forward(self):
        try:
            key_path = r"SYSTEM\CurrentControlSet\Services\Tcpip\Parameters"
            with winreg.OpenKey(winreg.HKEY_LOCAL_MACHINE, key_path) as key:
                value, _ = winreg.QueryValueEx(key, "IPEnableRouter")
                return value == 0, f"IPEnableRouter={value}"
        except Exception as e:
            return False, f"注册表读取失败: {e}"

    def check_promiscuous_mode(self):
        try:
            cmd = ['powershell', '-Command',
                   "(Get-NetAdapter | Where-Object { $_.PromiscuousMode -eq 'Enabled' }).Name"]
            result = subprocess.run(cmd, capture_output=True, text=True)
            if result.returncode == 0:
                interfaces = result.stdout.strip()
                if interfaces:
                    return False, f"混杂模式网卡: {interfaces}"
                else:
                    return True, "无混杂模式网卡"
            else:
                return False, f"PowerShell命令失败: {result.stderr}"
        except Exception as e:
            return False, f"检测异常: {e}"


    # 运行的函数
    def detect(self):
        result_report = []
        for rule in self.rules:
            try:
                safe, info = rule["check_function"]()
            except Exception as e:
                safe, info = False, f"检查异常: {e}"

            result_report.append({
                "rule_name": rule["name"],
                "status": info,
                "result": safe
            })
        data=json.dumps(result_report)
        return data