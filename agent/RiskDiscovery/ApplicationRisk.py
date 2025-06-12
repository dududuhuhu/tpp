import requests
import json
import time
import socket
import pymysql
from urllib.parse import urlparse
from datetime import datetime
from typing import List, Dict, Optional


class ApplicationRiskScanner:
    """
    应用风险扫描器
    """

    def __init__(self, db_config: Dict):
        """
        初始化应用风险扫描器
        :param db_config: 数据库配置
        """
        self.db_config = db_config
        self.db = None
        self.cursor = None
        self.connect_database()

    def connect_database(self):
        """
        连接数据库
        """
        try:
            self.db = pymysql.connect(
                host=self.db_config['host'],
                port=self.db_config['port'],
                user=self.db_config['user'],
                password=self.db_config['password'],
                database=self.db_config['database'],
                charset='utf8mb4'
            )
            self.cursor = self.db.cursor()
            print("数据库连接成功")
        except Exception as e:
            print(f"数据库连接失败: {e}")
            raise e

    def close_database(self):
        """
        关闭数据库连接
        """
        if self.cursor:
            self.cursor.close()
        if self.db:
            self.db.close()
        print("数据库连接已关闭")

    def get_application_risk_rules(self) -> List[Dict]:
        """
        从数据库获取应用风险检测规则
        :return: 风险检测规则列表
        """
        try:
            sql = """
            SELECT id, risk_name, risk_desc, risk_level, risk_type, 
                   target_service, detection_method, detection_path, 
                   detection_payload, risk_flag, remediation_advice
            FROM application_risk_rules 
            WHERE status = 'active'
            ORDER BY risk_level DESC
            """
            self.cursor.execute(sql)
            results = self.cursor.fetchall()

            rules = []
            for row in results:
                rule = {
                    'id': row[0],
                    'risk_name': row[1],
                    'risk_desc': row[2],
                    'risk_level': row[3],
                    'risk_type': row[4],
                    'target_service': row[5],
                    'detection_method': row[6],
                    'detection_path': row[7],
                    'detection_payload': row[8],
                    'risk_flag': row[9],
                    'remediation_advice': row[10]
                }
                rules.append(rule)

            print(f"成功获取 {len(rules)} 条应用风险检测规则")
            return rules

        except Exception as e:
            print(f"获取应用风险规则失败: {e}")
            return []

    def check_port_open(self, host: str, port: int, timeout: int = 3) -> bool:
        """
        检测端口是否开放
        :param host: 主机地址
        :param port: 端口号
        :param timeout: 超时时间
        :return: 端口是否开放
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

    def detect_application_risk(self, rule: Dict, target_host: str) -> Dict:
        """
        检测单个应用风险
        :param rule: 风险检测规则
        :param target_host: 目标主机
        :return: 检测结果
        """
        print(f"检测应用风险: {rule['risk_name']} - 目标: {target_host}")

        # 构建完整的检测URL
        if rule['detection_method'] == 'HTTP':
            target_url = f"http://{target_host}{rule['detection_path']}"
        elif rule['detection_method'] == 'HTTPS':
            target_url = f"https://{target_host}{rule['detection_path']}"
        else:
            target_url = f"http://{target_host}{rule['detection_path']}"

        # 初始化检测结果
        detection_result = {
            'rule_id': rule['id'],
            'risk_name': rule['risk_name'],
            'risk_type': rule['risk_type'],
            'risk_level': rule['risk_level'],
            'target_host': target_host,
            'target_url': target_url,
            'detection_time': datetime.now().strftime('%Y-%m-%d %H:%M:%S'),
            'is_risky': False,
            'response_content': '',
            'error_message': '',
            'remediation_advice': rule['remediation_advice']
        }

        try:
            # 根据检测方法执行相应的检测
            if rule['detection_method'] in ['HTTP', 'HTTPS']:
                detection_result = self._http_detection(rule, target_url, detection_result)
            elif rule['detection_method'] == 'PORT':
                detection_result = self._port_detection(rule, target_host, detection_result)
            elif rule['detection_method'] == 'SERVICE':
                detection_result = self._service_detection(rule, target_host, detection_result)

        except Exception as e:
            detection_result['error_message'] = str(e)
            print(f"检测异常: {e}")

        return detection_result

    def _http_detection(self, rule: Dict, target_url: str, result: Dict) -> Dict:
        """
        HTTP方式检测
        :param rule: 风险规则
        :param target_url: 目标URL
        :param result: 结果字典
        :return: 更新后的结果
        """
        try:
            headers = {
                'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36'
            }

            if rule['detection_payload']:
                # 如果有payload，添加到URL或作为POST数据
                if 'GET' in rule['detection_method'] or not rule['detection_payload'].startswith('{'):
                    target_url += rule['detection_payload']
                    response = requests.get(target_url, headers=headers, timeout=10)
                else:
                    # POST方式
                    response = requests.post(target_url, data=rule['detection_payload'], headers=headers, timeout=10)
            else:
                response = requests.get(target_url, headers=headers, timeout=10)

            result['response_content'] = response.text
            result['status_code'] = response.status_code

            # 检查风险标识
            if rule['risk_flag'] and rule['risk_flag'] in response.text:
                result['is_risky'] = True
                result['risk_detail'] = f"检测到风险标识: {rule['risk_flag']}"
                print(f"✓ 发现风险: {rule['risk_name']}")
            else:
                print(f"✗ 未发现风险: {rule['risk_name']}")

        except requests.exceptions.ConnectionError:
            result['error_message'] = "连接失败，目标服务可能未运行"
        except requests.exceptions.Timeout:
            result['error_message'] = "请求超时"
        except Exception as e:
            result['error_message'] = f"HTTP检测异常: {str(e)}"

        return result

    def _port_detection(self, rule: Dict, target_host: str, result: Dict) -> Dict:
        """
        端口检测方式
        :param rule: 风险规则
        :param target_host: 目标主机
        :param result: 结果字典
        :return: 更新后的结果
        """
        try:
            # 从detection_payload中获取端口号
            port = int(rule['detection_payload'])
            is_open = self.check_port_open(target_host, port)

            if is_open:
                result['is_risky'] = True
                result['risk_detail'] = f"端口 {port} 开放，存在安全风险"
                print(f"✓ 发现风险: {rule['risk_name']} - 端口 {port} 开放")
            else:
                result['risk_detail'] = f"端口 {port} 关闭"
                print(f"✗ 未发现风险: {rule['risk_name']} - 端口 {port} 关闭")

        except Exception as e:
            result['error_message'] = f"端口检测异常: {str(e)}"

        return result

    def _service_detection(self, rule: Dict, target_host: str, result: Dict) -> Dict:
        """
        服务检测方式
        :param rule: 风险规则
        :param target_host: 目标主机
        :param result: 结果字典
        :return: 更新后的结果
        """
        # 这里可以实现更复杂的服务检测逻辑
        # 例如：检测特定服务的版本、配置等
        try:
            # 示例：检测常见服务端口
            service_ports = {
                'tomcat': [8080, 8443, 8009],
                'ftp': [21, 22],
                'mysql': [3306],
                'redis': [6379],
                'mongodb': [27017]
            }

            service_name = rule['target_service'].lower()
            if service_name in service_ports:
                for port in service_ports[service_name]:
                    if self.check_port_open(target_host, port):
                        result['is_risky'] = True
                        result['risk_detail'] = f"{service_name.upper()} 服务在端口 {port} 运行，需要检查配置安全性"
                        print(f"✓ 发现服务: {service_name.upper()} 在端口 {port}")
                        break

        except Exception as e:
            result['error_message'] = f"服务检测异常: {str(e)}"

        return result

    def save_detection_result(self, result: Dict):
        """
        保存检测结果到数据库
        :param result: 检测结果
        """
        try:
            sql = """
            INSERT INTO application_risk_results 
            (rule_id, risk_name, risk_type, risk_level, target_host, target_url, 
             detection_time, is_risky, risk_detail, response_content, error_message, 
             remediation_advice, created_at)
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
            """

            values = (
                result['rule_id'],
                result['risk_name'],
                result['risk_type'],
                result['risk_level'],
                result['target_host'],
                result.get('target_url', ''),
                result['detection_time'],
                result['is_risky'],
                result.get('risk_detail', ''),
                result.get('response_content', '')[:1000],  # 限制长度
                result.get('error_message', ''),
                result.get('remediation_advice', ''),
                datetime.now()
            )

            self.cursor.execute(sql, values)
            self.db.commit()

        except Exception as e:
            print(f"保存检测结果失败: {e}")
            self.db.rollback()

    def batch_scan_targets(self, target_hosts: List[str]) -> List[Dict]:
        """
        批量扫描目标主机
        :param target_hosts: 目标主机列表
        :return: 扫描结果列表
        """
        print(f"开始批量扫描 {len(target_hosts)} 个目标主机")

        # 获取风险检测规则
        rules = self.get_application_risk_rules()
        if not rules:
            print("未获取到风险检测规则")
            return []

        all_results = []

        for i, target_host in enumerate(target_hosts, 1):
            print(f"\n[{i}/{len(target_hosts)}] 扫描目标: {target_host}")

            for rule in rules:
                result = self.detect_application_risk(rule, target_host)
                all_results.append(result)

                # 保存检测结果
                self.save_detection_result(result)

                # 添加延时避免过于频繁的请求
                time.sleep(0.5)

        return all_results

    def generate_report(self, results: List[Dict]) -> Dict:
        """
        生成扫描报告
        :param results: 扫描结果列表
        :return: 报告统计信息
        """
        total_scans = len(results)
        risks_found = len([r for r in results if r['is_risky']])
        errors = len([r for r in results if r['error_message']])

        # 按风险等级统计
        risk_levels = {}
        risk_types = {}

        for result in results:
            if result['is_risky']:
                level = result['risk_level']
                risk_type = result['risk_type']

                risk_levels[level] = risk_levels.get(level, 0) + 1
                risk_types[risk_type] = risk_types.get(risk_type, 0) + 1

        report = {
            'scan_summary': {
                'total_scans': total_scans,
                'risks_found': risks_found,
                'scan_errors': errors,
                'success_rate': f"{((total_scans - errors) / total_scans * 100):.1f}%" if total_scans > 0 else "0%"
            },
            'risk_distribution': {
                'by_level': risk_levels,
                'by_type': risk_types
            },
            'scan_time': datetime.now().strftime('%Y-%m-%d %H:%M:%S')
        }

        return report


def main():
    """
    主函数 - 应用风险扫描示例
    """
    # 数据库配置
    db_config = {
        'host': 'localhost',
        'port': 3306,
        'user': 'root',
        'password': '123456xsy',
        'database': 'threat_perception'
    }

    # 目标主机列表
    #这里跟换成自己的主机ip
    target_hosts = [
        '127.0.0.1',
        '192.168.43.148',
        '192.168.67.1'
    ]

    scanner = None
    try:
        # 创建扫描器实例
        scanner = ApplicationRiskScanner(db_config)

        # 执行批量扫描
        results = scanner.batch_scan_targets(target_hosts)

        # 生成扫描报告
        report = scanner.generate_report(results)

        # 输出报告
        print(f"\n=== 应用风险扫描报告 ===")
        print(f"扫描时间: {report['scan_time']}")
        print(f"总扫描次数: {report['scan_summary']['total_scans']}")
        print(f"发现风险: {report['scan_summary']['risks_found']}")
        print(f"扫描错误: {report['scan_summary']['scan_errors']}")
        print(f"成功率: {report['scan_summary']['success_rate']}")

        if report['risk_distribution']['by_level']:
            print(f"\n风险等级分布:")
            for level, count in report['risk_distribution']['by_level'].items():
                print(f"  等级 {level}: {count} 个")

        if report['risk_distribution']['by_type']:
            print(f"\n风险类型分布:")
            for risk_type, count in report['risk_distribution']['by_type'].items():
                print(f"  {risk_type}: {count} 个")

    except Exception as e:
        print(f"扫描过程异常: {e}")

    finally:
        if scanner:
            scanner.close_database()


if __name__ == "__main__":
    main()