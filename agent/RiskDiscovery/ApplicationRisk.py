import requests
import json
import time
import socket
from datetime import datetime
from typing import List, Dict


class ApplicationRiskScanner:
    """
    应用风险扫描器（适配驼峰命名规则字段，isRisky 为 0/1）
    """

    def __init__(self):
        pass

    def get_application_risk_rules(self) -> List[Dict]:
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

    def check_port_open(self, host: str, port: int, timeout: int = 3) -> bool:
        """
        检查端口是否开放
        """
        try:
            sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            sock.settimeout(timeout)
            result = sock.connect_ex((host, port))
            sock.close()
            return result == 0
        except Exception as e:
            print(f"端口检测异常: {e}")
            return False

    def detect_application_risk(self, rule: Dict, target_host: str,mac) -> Dict:
        """
        检测单条规则的风险
        """
        print(f"检测: {rule['riskName']} → {target_host}")
        if rule['detectionMethod'] == 'HTTP':
            target_url = f"http://{target_host}{rule['detectionPath']}"
        elif rule['detectionMethod'] == 'HTTPS':
            target_url = f"https://{target_host}{rule['detectionPath']}"
        else:
            target_url = f"http://{target_host}{rule['detectionPath']}"

        result = {
            'mac':mac,
            'ruleId': rule['id'],
            'riskName': rule['riskName'],
            'riskType': rule['riskType'],
            'riskLevel': rule['riskLevel'],
            'targetHost': target_host,
            'targetUrl': target_url,
            'isRisky': 0,
            'responseContent': '',
            'errorMessage': '',
            'remediationAdvice': rule['remediationAdvice']
        }

        try:
            if rule['detectionMethod'] in ['HTTP', 'HTTPS']:
                result = self._http_detection(rule, target_url, result)
            elif rule['detectionMethod'] == 'PORT':
                result = self._port_detection(rule, target_host, result)
            elif rule['detectionMethod'] == 'SERVICE':
                result = self._service_detection(rule, target_host, result)
        except Exception as e:
            result['errorMessage'] = str(e)

        return result

    def _http_detection(self, rule: Dict, target_url: str, result: Dict) -> Dict:
        """
        执行 HTTP 或 HTTPS 风险检测
        """
        try:
            headers = {
                'User-Agent': 'Mozilla/5.0'
            }

            if rule['detectionPayload']:
                if 'GET' in rule['detectionMethod'] or not rule['detectionPayload'].startswith('{'):
                    target_url += rule['detectionPayload']
                    response = requests.get(target_url, headers=headers, timeout=10)
                else:
                    response = requests.post(target_url, data=rule['detectionPayload'], headers=headers, timeout=10)
            else:
                response = requests.get(target_url, headers=headers, timeout=10)

            result['responseContent'] = response.text
            result['statusCode'] = response.status_code

            if rule['riskFlag'] and rule['riskFlag'] in response.text:
                result['isRisky'] = 1
                result['riskDetail'] = f"检测到风险标识: {rule['riskFlag']}"
                print(f"✓ 风险: {rule['riskName']}")
            else:
                result['isRisky'] = 0
                print(f"✗ 安全: {rule['riskName']}")

        except requests.exceptions.RequestException as e:
            result['errorMessage'] = str(e)

        return result

    def _port_detection(self, rule: Dict, target_host: str, result: Dict) -> Dict:
        """
        检查端口开放状态
        """
        try:
            port = int(rule['detectionPayload'])
            is_open = self.check_port_open(target_host, port)
            if is_open:
                result['isRisky'] = 1
                result['riskDetail'] = f"端口 {port} 开放"
                print(f"✓ 风险: 端口 {port} 开放")
            else:
                result['isRisky'] = 0
                result['riskDetail'] = f"端口 {port} 关闭"
                print(f"✗ 安全: 端口 {port} 关闭")
        except Exception as e:
            result['errorMessage'] = str(e)

        return result

    def _service_detection(self, rule: Dict, target_host: str, result: Dict) -> Dict:
        """
        服务探测（简易实现）
        """
        try:
            service_ports = {
                'tomcat': [8080, 8443],
                'ftp': [21],
                'mysql': [3306],
                'redis': [6379]
            }

            service = rule.get('targetService', '').lower()
            for port in service_ports.get(service, []):
                if self.check_port_open(target_host, port):
                    result['isRisky'] = 1
                    result['riskDetail'] = f"{service.upper()} 服务在端口 {port} 运行"
                    print(f"✓ 风险: {service.upper()} on {port}")
                    break
            else:
                result['isRisky'] = 0
        except Exception as e:
            result['errorMessage'] = str(e)

        return result

    def batch_scan_targets(self, target_hosts: List[str],mac) -> List[Dict]:
        """
        批量扫描目标
        """
        print(f"\n开始扫描 {len(target_hosts)} 个主机")
        rules = self.get_application_risk_rules()
        if not rules:
            print("未获取到规则")
            return []

        all_results = []

        for i, host in enumerate(target_hosts, 1):
            print(f"\n[{i}/{len(target_hosts)}] 扫描: {host}")
            for rule in rules:
                result = self.detect_application_risk(rule, host,mac)
                all_results.append(result)
                time.sleep(0.5)

        return all_results

    def generate_report(self, results: List[Dict]) -> Dict:
        """
        汇总扫描结果
        """
        total = len(results)
        risky = len([r for r in results if r['isRisky'] == 1])
        errors = len([r for r in results if r['errorMessage']])
        by_level = {}
        by_type = {}

        for r in results:
            if r['isRisky'] == 1:
                by_level[r['riskLevel']] = by_level.get(r['riskLevel'], 0) + 1
                by_type[r['riskType']] = by_type.get(r['riskType'], 0) + 1

        return {
            "scanSummary": {
                "totalScans": total,
                "risksFound": risky,
                "scanErrors": errors,
                "successRate": f"{((total - errors) / total * 100):.1f}%" if total else "0%"
            },
            "riskDistribution": {
                "byLevel": by_level,
                "byType": by_type
            },
            "scanTime": datetime.now().strftime('%Y-%m-%d %H:%M:%S'),
            "results": results
        }


def main():
    target_hosts = ['127.0.0.1']  # 可替换为其他目标

    scanner = ApplicationRiskScanner()
    results = scanner.batch_scan_targets(target_hosts)
    report = scanner.generate_report(results)

    # 输出 JSON 报告
    print("\n=== 扫描报告 JSON ===")
    print(json.dumps(report, indent=2, ensure_ascii=False))


if __name__ == "__main__":
    main()
