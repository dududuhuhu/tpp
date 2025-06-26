# coding=utf-8
import json
import uuid
import os
import sys
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
from baseline.BaselineHarden import BaselineHardening

class BaselineHardenDetect:
    """
    漏洞探测类（非线程版本）
    """

    def __init__(self,data):
        # 可扩展初始化参数（如扫描目标等）
        self.results = []
        self.data = data

    def detect(self) -> str:
        """
        外部调用接口：执行漏洞探测，返回 JSON 字符串
        """
        self.__BaselineHarden_detect(self.data)
        return json.dumps(self.results, ensure_ascii=False)

    def __BaselineHarden_detect(self,data):
        """
        内部实际探测逻辑
        """
        print("开始基线加固..............!")
        # 创建扫描器实例并执行
        hardening = BaselineHardening()
        self.results = hardening.execute_hardening(data)
        self.results["task_id"]=data["task_id"]
        # print(json.dumps(result, indent=4, ensure_ascii=False))

        print("基线加固结束！")
        return self.results

if __name__ == '__main__':
    request_data = {
        "type": "baselineHardening",
        "task_id":"11",
        "name": [
            "SysAccountPolicy::MinimumPasswordAge",
            "SysAccountPolicy::MaximumPasswordAge",
            "SysEventAuditPolicy::AuditSystemEvents",
            "SysSecurityOptionPolicy::Network_access_Restrict_anonymous_access_to_Named_Pipes_and_Shares",
            "InvalidItem"                       # 这个项目不存在，用于测试失败情况
        ]
    }
    baseline = BaselineHardenDetect(request_data)
    result = baseline.detect()
    print(result)
