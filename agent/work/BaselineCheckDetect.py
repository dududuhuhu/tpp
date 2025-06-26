# coding=utf-8
import json
import uuid
# import os
# import sys
# sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
from baseline.BaselineCheck import BaselineCheck

class BaselineCheckDetect:
    """
    漏洞探测类（非线程版本）
    """

    def __init__(self,data):
        # 可扩展初始化参数（如扫描目标等）
        self.results = []
        self.mac=':'.join(("%012X" % uuid.getnode())[i:i + 2] for i in range(0, 12, 2))

    def detect(self) -> str:
        """
        外部调用接口：执行漏洞探测，返回 JSON 字符串
        """
        self.__BaselineCheck_detect(self.mac)
        return json.dumps(self.results, ensure_ascii=False)

    def __BaselineCheck_detect(self,mac):
        """
        内部实际探测逻辑
        """
        print("开始基线核查..............!")
        # 创建扫描器实例并执行
        # mac=self.mac
        self.results = BaselineCheck(mac)

        print("基线核查结束！")
        return self.results

if __name__ == '__main__':
    baseline = BaselineCheckDetect("123456789012")
    result = baseline.detect()
    print(result)