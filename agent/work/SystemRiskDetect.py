import json
import subprocess
import datetime
import ntplib
import winreg
from typing import List, Dict

import requests

# 风险等级映射：数字 → 文字标签
RISK_LEVEL_MAP = {1: "低", 2: "中", 3: "高"}


class SystemRiskDetect:
    """
    Windows 平台的系统风险扫描器（支持注册表、NTP、命令检测）
    """

    def __init__(self,data):
        self.mac=data['macAddress']

    def get_application_risk_rules(self) -> List[Dict]:
        """
        从后端接口获取风险检测规则
        """
        try:
            url = "http://localhost:8080/rule/systemRisk"
            response = requests.get(url, timeout=10)
            response.raise_for_status()
            rules = response.json()
            print(f"获取到 {len(rules)} 条规则")
            return rules
        except requests.RequestException as e:
            print(f"[错误] 获取规则失败: {e}")
            return []
    def detect_system_risk(self, rule: Dict) -> Dict:
        """
        执行单条检测规则，并返回检测结果
        """
        result = {
            "mac":self.mac,
            "ruleId": rule["id"],
            "riskName": rule["riskName"],
            "riskLevel": rule["riskLevel"],
            "riskType": rule["riskType"],
            "isRisky": 0,  # 1=有风险，0=无风险
            "errorMessage": "",
            "detectionOutput": "",
            "riskDetail": "",
            "remediationAdvice": rule["remediationAdvice"]
        }

        try:
            if rule["detectionMethod"] == "NTP":
                # 使用 ntplib 获取 NTP 时间，并与本地时间比较
                client = ntplib.NTPClient()
                response = client.request('pool.ntp.org')
                ntp_time = datetime.datetime.utcfromtimestamp(response.tx_time) + datetime.timedelta(hours=8)
                local_time = datetime.datetime.now()
                offset = abs((local_time - ntp_time).total_seconds())

                result["detectionOutput"] = f"NTP时间: {ntp_time}, 本地时间: {local_time}"
                result["riskDetail"] = f"NTP时间偏差：{int(offset)} 秒"

                if offset > 300:
                    result["isRisky"] = 1

            elif rule["detectionMethod"] == "REG":
                # 读取注册表键值
                try:
                    with winreg.OpenKey(winreg.HKEY_LOCAL_MACHINE, rule["registryPath"]) as key:
                        value, _ = winreg.QueryValueEx(key, rule["valueName"])
                        result["detectionOutput"] = f"{rule['valueName']}={value}"
                        if str(value) == rule["riskFlag"]:
                            result["isRisky"] = 1
                            result["riskDetail"] = f"注册表值为 {value}"
                except FileNotFoundError:
                    result["errorMessage"] = "注册表路径或键不存在"
                except PermissionError:
                    result["errorMessage"] = "注册表访问权限不足"

            elif rule["detectionMethod"] == "CMD":
                # 执行 PowerShell 命令检查混杂模式
                output = subprocess.check_output(rule["detectionCommand"], shell=True).decode().strip()
                result["detectionOutput"] = output
                if output:
                    result["isRisky"] = 1
                    result["riskDetail"] = f"混杂模式网卡: {output}"

        except Exception as e:
            result["errorMessage"] = str(e)

        return result

    def detect(self) -> List[Dict]:
        """
        扫描所有系统风险规则，仅返回每条检测结果（简洁版）
        """
        print("开始系统风险探测...................!")
        rules = self.get_application_risk_rules()
        results=[]
        for rule in rules:
            result=self.detect_system_risk(rule)
            if result['isRisky']==1:
                results.append(result)
        # results = [self.detect_system_risk(rule) for rule in rules]

        results=json.dumps(results, ensure_ascii=False)
        print(results)
        print("探测系统风险结束！")
        return results


