# coding=utf-8
import json
import wmi
import pythoncom

class HotfixDetector:
    """
    补丁探测器：用于探测本机已安装的补丁（HotFixID）
    """

    def detect(self,data):
        """
        执行补丁探测并返回结果
        :return: JSON 字符串格式的补丁信息列表
        """
        print("开始探测补丁数据..............!")
        pythoncom.CoInitialize()

        try:
            c = wmi.WMI()
            hotfixes = c.query("SELECT HotFixID FROM Win32_QuickFixEngineering")

            hotfix_list = []
            for hotfix in hotfixes:
                info = {
                    'hotfixId': hotfix.HotFixID
                }
                hotfix_list.append(info)

            result = json.dumps(hotfix_list, ensure_ascii=False)
            print(result)
            print("探测补丁数据结束！")
            return result

        finally:
            pythoncom.CoUninitialize()
