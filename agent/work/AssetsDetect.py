import json
import winreg
import nmap
import pythoncom
import wmi


class AcountDetector:
    def __init__(self, data):
        self.data = data  # 保存外部传入的数据

    def detect(self):
        """
        探测账号的方法
        """
        print("开始探测账号数据.............!")
        pythoncom.CoInitialize()
        c = wmi.WMI()
        account_list = []

        for user in c.Win32_UserAccount():
            user_dict = {
                "mac": self.data.get("macAddress", ""),
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

        pythoncom.CoUninitialize()
        account_data = json.dumps(account_list)
        print(account_data)
        print("探测账号数据结束")
        return account_data


class ServiceDetector:
    def __init__(self, data):
        self.data = data  # 保存外部传入的数据

    def detect(self):
        """
        探测服务信息
        """
        print("开始探测服务数据.............!")
        nm = nmap.PortScanner()
        nm.scan('127.0.0.1', arguments='-sTV')

        state = nm.all_hosts()
        res_list = []
        print("servicedata:",self.data)
        # print("self.data.get('macAddress'):", self.data.get('macAddress'))
        if state:
            for host in state:
                for proto in nm[host].all_protocols():
                    lport = nm[host][proto].keys()
                    for port in lport:
                        nmap_res = {
                            'mac': self.data.get('macAddress', ''),
                            'protocol': proto,
                            'port': port,
                            'state': nm[host][proto][port]['state'],
                            'name': nm[host][proto][port]['name'],
                            'product': nm[host][proto][port]['product'],
                            'version': nm[host][proto][port]['version'],
                            'extrainfo': nm[host][proto][port]['extrainfo'],
                        }
                        res_list.append(nmap_res)
                        # print("nmap_res:", nmap_res)

        res_json = json.dumps(res_list)
        print(res_json)
        print("服务数据探测结束！")
        return res_json


class ProcessDetector:
    def __init__(self, data):
        self.data = data  # 保存外部传入的数据

    def detect(self):
        """
        探测进程信息
        """
        print('开始探测进程数据......!')
        pythoncom.CoInitialize()
        c = wmi.WMI()
        process_list = []
        for process in c.Win32_Process():
            process_info = {
                'mac': self.data.get('macAddress', ''),
                'pid': process.ProcessId,
                'ppid': process.ParentProcessId,
                'name': process.Name,
                'cmd': process.CommandLine,
                'priority': process.Priority,
                'description': process.Description,
            }
            process_list.append(process_info)



        pythoncom.CoUninitialize()
        process_data = json.dumps(process_list)
        print(process_data)
        print('进程数据探测结束！')
        return process_data


class AppDetector:
    def __init__(self, data):
        self.data = data  # 保存外部传入的数据

    def detect(self):
        """
        探测注册表中的已安装应用信息
        """
        print('开始探测app数据......!')
        software_list = []
        try:
            registry_key = winreg.OpenKey(winreg.HKEY_LOCAL_MACHINE,
                                          r'SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall')
            number = winreg.QueryInfoKey(registry_key)[0]
            for i in range(number):
                try:
                    sub_key_name = winreg.EnumKey(registry_key, i)
                    sub_key = winreg.OpenKey(registry_key, sub_key_name)
                    software = {
                        'mac': self.data.get('macAddress', '')
                    }
                    try:
                        software['display_name'] = winreg.QueryValueEx(sub_key, 'DisplayName')[0]
                        software['install_location'] = winreg.QueryValueEx(sub_key, 'InstallLocation')[0]
                        software['uninstall_string'] = winreg.QueryValueEx(sub_key, 'UninstallString')[0]
                        software_list.append(software)
                    except WindowsError:
                        continue
                except WindowsError:
                    break
        except Exception as e:
            print("读取注册表失败:", e)

        app_data = json.dumps(software_list)
        print(app_data)
        print("APP数据探测结束！")
        return app_data
