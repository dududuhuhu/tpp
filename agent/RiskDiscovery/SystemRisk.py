


system_risk_rules = [
    {
        "id": 1,
        "name": "服务器时间校验",
        "description": "服务器时间与NTP偏差大于5分钟",
        "check_function": "check_time_sync"
    },
    {
        "id": 3,
        "name": "网卡混杂模式",
        "description": "网卡处于混杂模式，可能用于嗅探网络数据",
        "check_function": "check_promiscuous_mode"
    }
]
import datetime
import ntplib
import winreg
import subprocess

def check_time_sync(threshold_minutes=5):
    try:
        client = ntplib.NTPClient()
        response = client.request('pool.ntp.org')
        ntp_time = datetime.datetime.utcfromtimestamp(response.tx_time)
        local_time = datetime.datetime.utcnow()
        delta = abs((ntp_time - local_time).total_seconds() / 60)
        return delta <= threshold_minutes, f"NTP差值: {delta:.2f}分钟"
    except Exception as e:
        return False, f"NTP同步检查失败: {e}"

def check_ip_forward():
    try:
        key_path = r"SYSTEM\CurrentControlSet\Services\Tcpip\Parameters"
        with winreg.OpenKey(winreg.HKEY_LOCAL_MACHINE, key_path) as key:
            value, regtype = winreg.QueryValueEx(key, "IPEnableRouter")
            return value == 0, f"IPEnableRouter={value}"
    except Exception as e:
        return False, f"注册表读取失败: {e}"

def check_promiscuous_mode():
    try:
        # 调用 netsh 命令查看网卡信息，判断混杂模式
        # Windows没直接命令显示混杂模式，这里示例用powershell脚本检测
        # 这个检测相对复杂，这里用示例命令代替，需要管理员权限
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

def run_system_risk_scan():
    result_report = []
    for rule in system_risk_rules:
        func = globals().get(rule["check_function"])
        if not func:
            result_report.append({
                "rule_name": rule["name"],
                "status": "未实现",
                "result": False,
            })
            continue
        safe, info = func()
        result_report.append({
            "rule_name": rule["name"],
            "status": info,
            "result": safe,
        })
    print(result_report)
    return result_report
"""
输出：[{'rule_name': '服务器时间校验', 'status': 'NTP差值: 0.01分钟', 'result': True},
 {'rule_name': '网卡混杂模式', 'status': '无混杂模式网卡', 'result': True}]
"""


if __name__ == "__main__":
    results = run_system_risk_scan()

