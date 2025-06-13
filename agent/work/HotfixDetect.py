# coding=utf-8
import json
import threading
import wmi
import pythoncom


class HotfixDetect(threading.Thread):
    """
    补丁探测的线程类
    """
    def __init__(self,mq,data):
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
        self.__hotfixDetect()

    def __hotfixDetect(self):
        ###补丁发现
        print("开始探测补丁数据..............!")
        pythoncom.CoInitialize()
        # 创建 WMI 客户端
        c = wmi.WMI()
        # 查询已安装的补丁列表
        hotfixes = c.query("SELECT HotFixID FROM Win32_QuickFixEngineering")

        # 组装补丁信息列表
        hotfix_list = []
        for hotfix in hotfixes:
            data = {
                # 'mac': self.__data['mac'],         # 获取 MAC 地址
                'hotfixId': hotfix.HotFixID  # 补丁编号
            }
            hotfix_list.append(data)

        # 释放 COM 资源
        pythoncom.CoUninitialize()

        # 将补丁信息转换为 JSON 字符串
        hotfix_data = json.dumps(hotfix_list)
        print(hotfix_data)
        print("探测补丁数据结束！")
        return hotfix_data
