import json
import threading
import winreg

import wmi
import pythoncom
import nmap

###补丁发现
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
        'hotfixId': hotfix.HotFixID        # 补丁编号
    }
    hotfix_list.append(data)

# 释放 COM 资源
pythoncom.CoUninitialize()

# 将补丁信息转换为 JSON 字符串
data = json.dumps(hotfix_list)
print(data)



