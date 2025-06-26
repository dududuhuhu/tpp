import subprocess
import json
import os

current_dir = os.path.dirname(os.path.abspath(__file__))
script_path = os.path.join(current_dir, 'ps', "baseline_hardening_20250625_1600.ps1")
# 设置策略，允许执行 PowerShell 脚本
set_policy_cmd = [
    'powershell',
    '-Command',
    'Set-ExecutionPolicy Unrestricted -Force'
]
subprocess.run(set_policy_cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

# 构建 PowerShell 执行命令
ps_command = f'powershell -ExecutionPolicy Bypass -File "{script_path}"'
result = subprocess.run(
    ps_command,
    stdout=subprocess.PIPE,
    stderr=subprocess.PIPE,
    text=True,
    shell=True
)

# 输出日志
print("========== PowerShell 执行输出 ==========")
print(result.stdout.strip())
print("========== PowerShell 错误输出 ==========")
print(result.stderr.strip())

# 判断是否成功
if result.returncode == 0 and "error" not in result.stderr.lower():
    execution_result = "成功"
else:
    execution_result = f"失败: {result.stderr.strip() or '未知错误'}"
    print(f"❌ PowerShell 执行失败，错误信息: {result.stderr.strip()}")
print(execution_result)