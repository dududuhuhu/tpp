# coding=utf-8
import json
from RiskDiscovery.ApplicationRisk import ApplicationRiskScanner

class ApplicationRiskDetect:
    """
    应用风险探测类（非线程版本）
    """

    def __init__(self, data):
        """
        初始化探测器
        :param ip_address: 目标主机 IP 地址
        """
        self.ip = data['ipAddress']
        self.db_config = {
            'host': 'localhost',
            'port': 3306,
            'user': 'root',
            'password': 'shuhan',
            'database': 'threat_perception'
        }

    def detect(self) -> str:
        """
        外部调用接口，执行探测任务
        :return: 探测结果（JSON字符串）
        """
        return self.__application_risk_detect()

    def __application_risk_detect(self) -> str:
        """
        执行应用风险探测
        :return: 探测结果（JSON字符串）
        """
        target_hosts = [self.ip]
        flag = 0
        scanner = None

        try:
            # 创建扫描器实例
            scanner = ApplicationRiskScanner(self.db_config)

            # 执行扫描
            results = scanner.batch_scan_targets(target_hosts)

            # 生成报告
            report = scanner.generate_report(results)

            # 输出摘要
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

            flag = 1

        except Exception as e:
            print(f"扫描过程异常: {e}")

        finally:
            if scanner:
                scanner.close_database()

        result = {'result': 'success' if flag == 1 else 'fail'}
        return json.dumps(result, ensure_ascii=False)
