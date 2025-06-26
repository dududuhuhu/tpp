#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import json
import subprocess
import os
from datetime import datetime
import platform


class BaselineHardening:
    def __init__(self):
        """
        初始化基线加固类
        :param map_file_path: 映射文件路径
        """
        map_file_path=os.path.join(os.path.dirname(__file__), "map.json")
        self.map_file_path = map_file_path
        self.mapping_data = self.load_mapping_data()
        # 获取配置文件路径 - 相对于脚本目录
        self.cfg_path = os.path.join(os.path.dirname(__file__), "config.cfg")
        self.powershell_template = '''
        Function Parse-SecPol($CfgFile) {{
            secedit /export /cfg "$CfgFile" | out-null
            $obj = New-Object psobject
            $index = 0
            $contents = Get-Content $CfgFile -raw
            [regex]::Matches($contents,"(?<=\\[)(.*)(?=\\])") | %{{ 
                $title = $_
                [regex]::Matches($contents,"(?<=\\]).*?((?=\\[)|(\\Z))", [System.Text.RegularExpressions.RegexOptions]::Singleline)[$index] | %{{ 
                    $section = New-Object psobject
                    $_.value -split "\\r\\n" | ?{{ $_.length -gt 0 }} | %{{ 
                        $value = [regex]::Match($_,"(?<=\\=).*").value
                        $name = [regex]::Match($_,".*(?=\\=)").value
                        $section | Add-Member -MemberType NoteProperty -Name $name.ToString().Trim() -Value $value.ToString().Trim() -ErrorAction SilentlyContinue | Out-Null
                    }}
                    $obj | Add-Member -MemberType NoteProperty -Name $title -Value $section
                }}
                $index += 1
            }}
            return $obj
        }}

        Function Set-SecPol($Object, $CfgFile) {{
            $Object.psobject.Properties.GetEnumerator() | %{{ 
                "[$($_.Name)]"
                $_.Value | %{{ 
                    $_.psobject.Properties.GetEnumerator() | %{{ 
                        "$($_.Name)=$($_.Value)"
                    }}
                }}
            }} | Out-File $CfgFile -ErrorAction Stop
            secedit /configure /db c:\\windows\\security\\local.sdb /cfg "$CfgFile"
        }}

        $ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
        $CfgPath = Join-Path $ScriptDir "config.cfg"
        $BackupFile = Join-Path $ScriptDir "config_backup_$(Get-Date -Format 'yyyyMMdd_HHmmss').cfg"
        Copy-Item $CfgPath $BackupFile -ErrorAction SilentlyContinue

        $SecPool = Parse-SecPol -CfgFile $CfgPath

        {hardening_commands}

        Set-SecPol -Object $SecPool -CfgFile $CfgPath

        Write-Host "Baseline hardening completed"
        '''

    def load_mapping_data(self):
        """
        加载映射文件数据
        :return: 映射数据字典
        """
        try:
            with open(self.map_file_path, 'r', encoding='utf-8') as f:
                data = json.load(f)

            # 转换为字典格式便于查找
            mapping_dict = {}
            for item in data:
                mapping_dict[item['name']] = item['recommended_value']

            return mapping_dict
        except Exception as e:
            print(f"Error loading mapping file: {str(e)}")
            return {}

    def parse_policy_name(self, full_name):
        """
        解析策略名称，提取section和property
        例如: "SysAccountPolicy::MinimumPasswordAge" -> ("System Access", "MinimumPasswordAge")
        """
        # 定义映射关系
        section_mapping = {
            # 基于实际PowerShell检查脚本的映射关系
            "SysAccountPolicy": "System Access",  # 对应SysAccountPolicy检查结果
            "SysEventAuditPolicy": "Event Audit",  # 对应SysEventAuditPolicy检查结果
            "SysUserPrivilegePolicy": "Privilege Rights",  # 对应SysUserPrivilegePolicy检查结果
            "SysSecurityOptionPolicy": "Registry Values",  # 对应SysSecurityOptionPolicy检查结果
            # ... 其他映射保持不变
        }
        if "::" in full_name:
            prefix, property_name = full_name.split("::", 1)
            section = section_mapping.get(prefix, "System Access")  # 默认为System Access
            return section, property_name
        else:
            return "System Access", full_name

    def generate_hardening_commands(self, hardening_items):
        """
        生成PowerShell加固命令
        :param hardening_items: 要加固的项目列表
        :return: PowerShell命令字符串和结果信息
        """
        commands = []
        results = []

        for item_name in hardening_items:
            if item_name in self.mapping_data:
                recommended_value = self.mapping_data[item_name]
                section, property_name = self.parse_policy_name(item_name)

                # 生成PowerShell命令
                command = f"$SecPool.'{section}'.{property_name} = {recommended_value}"
                commands.append(f"# {item_name}")
                commands.append(command)

                # 记录成功结果
                results.append({
                    "name": item_name,
                    "result": "成功"
                })
            else:
                # 记录失败结果
                results.append({
                    "name": item_name,
                    "result": "失败"
                })

        return "\n".join(commands), results

    def execute_hardening(self, request_data):
        """
        执行基线加固
        :param request_data: 请求数据 {"type":"baselineHardening","name": [...], "taskId": "1"}
        :return: 每项加固结果的列表
        """
        try:
            # 检查请求类型
            if request_data.get("type") != "baselineHardening":
                return [{
                    "mac": self.get_mac_address(),
                    "taskId": request_data.get("taskId", ""),
                    "name": "Invalid request type",
                    "result": "失败"
                }]

            hardening_items = request_data.get("name", [])
            task_id = request_data.get("taskId", "")
            mac_address = self.get_mac_address()

            if not hardening_items:
                return [{
                    "mac": mac_address,
                    "taskId": task_id,
                    "name": "No hardening items provided",
                    "result": "失败"
                }]

            # 生成 PowerShell 加固命令
            hardening_commands, item_results = self.generate_hardening_commands(hardening_items)

            if not hardening_commands.strip():
                return [{
                    "mac": mac_address,
                    "taskId": task_id,
                    "name": "No valid hardening items found",
                    "result": "失败"
                }]

            # 生成完整 PowerShell 脚本
            full_script = self.powershell_template.format(hardening_commands=hardening_commands)
            current_dir = os.path.dirname(os.path.abspath(__file__))
            timestamp = datetime.now().strftime("%Y%m%d_%H%M")
            script_filename = f'baseline_hardening_{timestamp}.ps1'
            script_path = os.path.join(current_dir, 'ps', script_filename)

            with open(script_path, 'w', encoding='utf-8') as f:
                f.write(full_script)

            if platform.system() == "Windows":
                try:
                    subprocess.run([
                        'powershell',
                        '-Command',
                        'Set-ExecutionPolicy Unrestricted -Force'
                    ], stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

                    ps_command = f'powershell -ExecutionPolicy Bypass -File "{script_path}"'
                    result = subprocess.run(
                        ps_command,
                        stdout=subprocess.PIPE,
                        stderr=subprocess.PIPE,
                        text=True,
                        shell=True
                    )

                    print("========== PowerShell 执行输出 ==========")
                    print(result.stdout.strip())
                    print("========== PowerShell 错误输出 ==========")
                    print(result.stderr.strip())

                    # 是否整体执行成功
                    overall_success = result.returncode == 0 and "error" not in result.stderr.lower()

                except subprocess.TimeoutExpired:
                    overall_success = False
                except Exception as e:
                    print(f"异常: {str(e)}")
                    overall_success = False
            else:
                print("非Windows系统，模拟执行...")
                print(full_script)
                overall_success = True  # 模拟为成功

            # 构造每一项的返回结果
            final_results = []
            for item in item_results:
                final_results.append({
                    "mac": mac_address,
                    "taskId": task_id,
                    "name": item["name"],
                    "result": item["result"] if overall_success else "失败"
                })

            return final_results

        except Exception as e:
            return [{
                "mac": self.get_mac_address(),
                "taskId": request_data.get("taskId", ""),
                "name": f"Execution error: {str(e)}",
                "result": "失败"
            }]

    def get_mac_address(self):
        """
        获取MAC地址
        :return: MAC地址字符串
        """
        try:
            import uuid
            mac = ':'.join(['{:02x}'.format((uuid.getnode() >> ele) & 0xff)
                            for ele in range(0, 8 * 6, 8)][::-1])
            return mac
        except:
            return "00:00:00:00:00:00"


def main():
    """
    主函数，演示用法
    """

    # map.json路径可能需要修改
    # 初始化基线加固器
    hardening = BaselineHardening()

    # 示例请求数据
    # 示例请求数据，包含各种类型的策略名称
    request_data = {
        "type": "baselineHardening",
        "task_id":" ",
        "name": [
            "SysAccountPolicy::MinimumPasswordAge",
            "SysAccountPolicy::MaximumPasswordAge",
            "SysEventAuditPolicy::AuditSystemEvents",
            "SysSecurityOptionPolicy::Network_access_Restrict_anonymous_access_to_Named_Pipes_and_Shares",
            "InvalidItem"                       # 这个项目不存在，用于测试失败情况
        ]
    }

    # 执行基线加固
    result = hardening.execute_hardening(request_data)

    # 打印结果
    print("基线加固执行结果:")
    print(json.dumps(result, indent=4, ensure_ascii=False))


if __name__ == "__main__":
    main()
