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
        self.mac=data['macAddress']
        self.ip = data['ipAddress']
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
        print("开始应用风险探测...................!")
        target_hosts = [self.ip]
        mac=self.mac
        scanner = None

        try:
            # 创建扫描器实例
            scanner = ApplicationRiskScanner()
            # 执行扫描
            results = scanner.batch_scan_targets(target_hosts,mac)
            # 生成报告
            report = scanner.generate_report(results)
            print(report)

            # 输出摘要
            print(f"\n=== 应用风险扫描报告 ===")
            print(f"扫描时间: {report['scanTime']}")
            print(f"总扫描次数: {report['scanSummary']['totalScans']}")
            print(f"发现风险: {report['scanSummary']['risksFound']}")
            print(f"扫描错误: {report['scanSummary']['scanErrors']}")
            print(f"成功率: {report['scanSummary']['successRate']}")

            if report['riskDistribution']['byLevel']:
                print(f"\n风险等级分布:")
                for level, count in report['riskDistribution']['byLevel'].items():
                    print(f"  等级 {level}: {count} 个")

            if report['riskDistribution']['byType']:
                print(f"\n风险类型分布:")
                for risk_type, count in report['riskDistribution']['byType'].items():
                    print(f"  {risk_type}: {count} 个")


        except Exception as e:
            print(f"扫描过程异常: {e}")

        print("探测应用风险结束！")
        results=json.dumps(results, indent=4, ensure_ascii=False)

        return results
