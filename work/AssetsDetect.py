# coding=utf-8
import json
import threading
import winreg

import wmi
import pythoncom
import nmap

class AssetsDetect(threading.Thread):
    """
    资产探测的线程类
    """
    def __init__(self,mq,data):
        super().__init__()
        # mq对象
        self.__mq=mq
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
        # 因为需要去探测4个类型的资产: account ,service,process,app
        account = self.__data["account"]
        service = self.__data["service"]
        process = self.__data["process"]
        app = self.__data["app"]

        if account ==1:
            self.__account_detect()
        if service ==1:
            self.__service_detect()
        if process ==1:
            self.__process_detect()
        if app ==1:
            self.__app_detect()

    def __account_detect(self):
        """
        探测账号的方法
        :return:
        """
        print("开始探测账号数据.............!")
        # 初始化
        pythoncom.CoInitialize()
        c = wmi.WMI()
        account_list = []
        # 获取所有用户
        for user in c.Win32_UserAccount():
            user_dict = {
                "mac":self.__data["macAddress"],
                "name": user.Name,
                "full_name": user.FullName,
                "sid": user.SID,
                "sidType": user.SIDType,
                "status": user.Status,
                "disabled": user.Disabled,
                "lockout": user.Lockout,
                "passwordChangeable": user.PasswordChangeable,
                "passwordExpires": user.PasswordExpires,
                "passwordRequired": user.PasswordRequired,
            }
            account_list.append(user_dict)
        # 去初始化
        pythoncom.CoInitialize()
        # 转换成json字符串
        account_data = json.dumps(account_list)
        print(account_data)
        # 将探测到的账号信息发送到对应的队列
        self.__mq.produce_account_info(account_data)
        print("探测账号数据结束")

    def __service_detect(self):
        """
        探测服务
        :return:
        """
        print("开始探测服务数据.............!")
        # 创建一个扫描仪对象
        nm=nmap.PortScanner()
        # 扫描目标主机
        nm.scan('127.0.0.1', arguments='-sTV') # 指定扫描端口范围

        # 获取扫描结果
        state = nm.all_hosts()
        # 装最终结果的
        res_list=[]
        if state:
            for host in nm.all_hosts():
                for proto in nm[host].all_protocols():
                    lport = nm[host][proto].keys()
                    for port in lport:
                        # 接受nmap扫描结果
                        nmap_res = {
                            'mac':self.__data['macAddress'],
                            'protocol':proto,
                            'port':port,
                            'state':nm[host][proto][port]['state'],
                            'name':nm[host][proto][port]['name'],
                            'product':nm[host][proto][port]['product'],
                            'version':nm[host][proto][port]['version'],
                            'extrainfo':nm[host][proto][port]['extrainfo'],
                        }
                        res_list.append(nmap_res)
        # 转换成JSON字符串
        res_json = json.dumps(res_list)
        print(res_json)
        # 发送到队列
        self.__mq.produce_service_info(res_json)
        print("服务数据探测结束！")

    def __process_detect(self):
        # 获取进程信息
        # 初始化
        print('开始探测进程数据......!')
        pythoncom.CoInitialize()
        c = wmi.WMI()
        process_list = []
        for process in c.Win32_Process():
            process_info = {
                'mac': self.__data['macAddress'],
                'pid': process.ProcessId,
                'ppid': process.ParentProcessId,
                'name': process.Name,
                'cmd':  process.CommandLine,
                'priority': process.Priority,
                'description': process.Description,
            }
            process_list.append(process_info)
        # 去初始化
        pythoncom.CoUninitialize()
        # 转换成JSON
        process_data = json.dumps(process_list)
        print(process_data)
        # 发送到队列
        self.__mq.produce_process_info(process_data)
        print('进程数据探测结束！')

    def __app_detect(self):
        # 从注册表获取软件信息
        print('开始探测app数据......!')
        registry_key = winreg.OpenKey(winreg.HKEY_LOCAL_MACHINE,
                                      r'SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall')
        software_list = []
        # 获取软件数量
        number = winreg.QueryInfoKey(registry_key)[0]
        for i in range(number):
            try:
                sub_key_name = winreg.EnumKey(registry_key, i)
                sub_key = winreg.OpenKey(registry_key, sub_key_name)
                software = {}
                try:
                    software['mac'] = self.__data['macAddress']
                    software['display_name'] = winreg.QueryValueEx(sub_key,
                                                                   'DisplayName')[0]
                    software['install_location'] = winreg.QueryValueEx(sub_key,
                                                                       'InstallLocation')[0]
                    software['uninstall_string'] = winreg.QueryValueEx(sub_key,
                                                                       'UninstallString')[0]
                    software_list.append(software)
                except WindowsError:
                    continue
            except WindowsError:
                break
        # 转换成JSON字符串
        app_data = json.dumps(software_list)
        print(app_data)
        self.__mq.prodocu_app_info(app_data)
        print("APP数据探测结束！")




