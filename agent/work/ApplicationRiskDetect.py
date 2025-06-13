# coding=utf-8
import json
import threading
import wmi
import pythoncom

from RiskDiscovery.ApplicationRisk import ApplicationRiskScanner


class ApplicationRiskDetect(threading.Thread):
    """
    应用探测的线程类
    """
    def __init__(self,data):
        super().__init__()
        # 平台传递的指令
        self.__data=data

    def run(self):
        """
        线程运行方法
        :return:
        """
        self.__detect()
        # pass

    def __detect(self):
        """
        探测的方法
        :return:
        """
        self.__applicationRiskDetect()

    def __applicationRiskDetect(self):
        # 这里需要后端传进来ipAddress
        ip=self.data['ipAddress']

        # 数据库配置
        db_config = {
            'host': 'localhost',
            'port': 3306,
            'user': 'root',
            'password': 'shuhan',
            'database': 'threat_perception'
        }

        # 目标主机列表
        # 这里跟换成自己的主机ip
        target_hosts = [ip]
        flag=0 # 成功1，失败0
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
            flag=1

        except Exception as e:
            print(f"扫描过程异常: {e}")

        finally:
            if scanner:
                scanner.close_database()

        if flag==1:
            data = {
               'result':'success'
            }
        else:
            data = {
               'result':'fail'
            }
        app_data=json.dumps(data)
        return app_data
