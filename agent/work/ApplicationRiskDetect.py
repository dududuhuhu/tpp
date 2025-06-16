# coding=utf-8
import json
import platform
import subprocess
import requests

if platform.platform().startswith("Windows"):
    SHELL = "powershell.exe"
    SH_FLAG = "-Command"
elif platform.platform().startswith("Linux"):
    SHELL="sh"
    SH_FLAG = "-c"
else:
    raise NotImplementedError("当前平台不支持，仅支持 Windows 和 Linux")

class ApplicationRiskDetect:
    def __init__(self, data):
        self._mac = data['macAddress']
    
    def _get_application_risk_rules(self) -> list[dict]:
        """
        从后端接口获取风险检测规则
        """
        try:
            url = "http://localhost:8080/rule/applicationRisk"
            response = requests.get(url, timeout=10)
            response.raise_for_status()
            rules = response.json()
            print(f"获取到 {len(rules)} 条规则")
            return rules
        except requests.RequestException as e:
            print(f"[错误] 获取规则失败: {e}")
            return []
    
    def _detect_application_risk(self, rule:dict) -> dict | None:
        """
        执行单条检测规则，并返回检测结果
        """
        try:
            cmd = rule['detectionPayload']
            result = subprocess.run(
                SHELL, SH_FLAG, cmd, capture_output=True, text=True
            )
            rule_id = rule['id']
            if result.returncode != 0:
                return {
                    'id':rule_id,
                    'macAddress': self.mac,
                }
            else:
                return None
        except Exception as e:
            print(f"[错误] 执行规则 {rule['id']} 检测失败: {e}")
    
    def detect(self) -> list[dict]:
        """
        扫描所有系统风险规则，仅返回每条检测结果（简洁版）
        """
        print("开始系统风险探测...................!")
        rules = self._get_application_risk_rules()
        results = []
        for rule in rules:
            result = self._detect_application_risk(rule)
            if result is not None:
                results.append(result)

        results=json.dumps(results, ensure_ascii=False)
        print(results)
        print("探测系统风险结束！")
        return results

