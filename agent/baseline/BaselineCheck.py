import subprocess
import json
# # 第一步：设置执行策略为 Unrestricted（跳过确认）
# set_policy_cmd = [
#     'powershell',
#     '-Command',
#     'Set-ExecutionPolicy Unrestricted -Force'
# ]
# subprocess.run(set_policy_cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
#
# # 第二步：执行你的 PowerShell 脚本
# ps_command = 'powershell -ExecutionPolicy Bypass -File ./ps/windows.ps1'
# result = subprocess.run(ps_command, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True, shell=True)
#
# # 打印输出和错误信息
# print(result.stdout)
# print(result.stderr)
# with open("baseLine.log","r",encoding="utf-8") as f:
#     for line in f.readlines():
#         if "合格项" in line or "异常项" in line:
            # print(line.strip())
"""
返回值json元素，其中的每一个元素为一个字典，包含以下字段：
baseLine.log每一行数据为：Registry::ScreenSaveTimeOut    [合格项]|300|600|系统基配核查-屏幕保护程序启动时间策略-【符合】等级保护标准.

{
	“name”:     基线核查项(例子为：Registry::ScreenSaveTimeOut),
	“result”:”合格项”或“异常项”（例子为：“合格项”),
	“current_value”:    当前值（例子为：300）,
	“recommended_value”:  推荐值（例子为：600）,
	“description”: 描述  （例子为：系统基配核查-屏幕保护程序启动时间策略-【符合】等级保护标准）,
｝

"""

def BaselineCheck(mac):
    set_policy_cmd = [
        'powershell',
        '-Command',
        'Set-ExecutionPolicy Unrestricted -Force'
    ]
    subprocess.run(set_policy_cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

    # 第二步：执行你的 PowerShell 脚本
    ps_command = 'powershell -ExecutionPolicy Bypass -File ./ps/windows.ps1'
    result = subprocess.run(ps_command, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True, shell=True)
    results = []
    entry = {}

    with open("baseLine.log", "r", encoding="utf-8") as f:
        lines = f.readlines()
    i = 0
    while i < len(lines):
        line = lines[i].strip()

        if line.startswith("Name") and ("合格项" in lines[i+2] or "异常项" in lines[i+2]):
            try:
                # 提取 name
                name_line = lines[i].strip()
                name = name_line.split(":", 1)[1].strip()

                # 跳过 Key 行（可选）
                key_line = lines[i+1].strip()

                # 提取 value
                value_line = lines[i+2].strip()
                value_part = value_line.split(":", 1)[1].strip()
                parts = value_part.split('|', 3)

                result_raw = parts[0].strip('[]')
                current_value = parts[1].strip()
                recommended_value = parts[2].strip()
                description = parts[3].strip()

                entry = {
                    "mac": mac,
                    "name": name,
                    "result": result_raw,
                    "current_value": current_value,
                    "recommended_value": recommended_value,
                    "description": description
                }
                results.append(entry)

                i += 3  # 跳过这三行
            except Exception as e:
                print(f"[!] 解析失败：第{i+1}行，错误：{e}")
                i += 1
        else:
            i += 1
    return results


