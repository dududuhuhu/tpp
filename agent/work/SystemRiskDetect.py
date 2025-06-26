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

    def __init__(self, data):
        self.mac = data['macAddress']
        self.rules = data.get('rules', [])  # 直接用传入的规则列表

    def _get_application_risk_rules(self) -> list[dict]:
        """
        获取系统风险规则列表
        """
        print(f"获取了{len(self.rules)}条规则")
        print(self.rules)
        return self.rules

    def detect_system_risk(self, rule: Dict) -> Dict:
        result = {
            "mac": self.mac,
            "ruleId": rule["id"],
            "riskName": rule["riskName"],
            "riskLevel": rule["riskLevel"],
            "riskType": rule["riskType"],
            "isRisky": 0,
            "errorMessage": "",
            "detectionOutput": "",
            "riskDetail": "",
            "remediationAdvice": rule["remediationAdvice"]
        }

        try:
            if rule["detectionMethod"] == "NTP":
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
                if "|" in rule["rulePath"]:
                    registry_path, value_name = rule["rulePath"].split("|", 1)
                else:
                    raise ValueError("rulePath 格式错误，应为 '路径|键名'")

                with winreg.OpenKey(winreg.HKEY_LOCAL_MACHINE, registry_path) as key:
                    value, _ = winreg.QueryValueEx(key, value_name)
                    result["detectionOutput"] = f"{value_name}={value}"
                    if str(value) == str(rule["rulePayload"]):
                        result["isRisky"] = 1
                        result["riskDetail"] = f"注册表值为 {value}"

            elif rule["detectionMethod"] == "CMD":
                output = subprocess.check_output(rule["rulePath"], shell=True).decode().strip()
                result["detectionOutput"] = output
                if output:
                    result["isRisky"] = 1
                    result["riskDetail"] = f"检测输出: {output}"

        except Exception as e:
            result["errorMessage"] = str(e)

        return result

    def detect(self) -> List[Dict]:
        """
        扫描所有系统风险规则，仅返回每条检测结果（简洁版）
        """
        print("开始系统风险探测...................!")
        rules = self._get_application_risk_rules()
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


