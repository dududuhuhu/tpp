import json
import uuid
from datetime import datetime, timedelta
from Logs.logs import get_log_info, EVENT_ACTION_MAP


def to_beijing_time(utc_str):
    try:
        utc_dt = datetime.strptime(utc_str, "%Y-%m-%d %H:%M:%S.%f %Z")
    except ValueError:
        utc_dt = datetime.strptime(utc_str, "%Y-%m-%d %H:%M:%S.%f")
    return (utc_dt + timedelta(hours=8)).strftime("%Y-%m-%d %H:%M:%S")

class AuditLogDetector:
    def __init__(self, data: dict):
        self.path = r"C:\Windows\System32\winevt\Logs\Security.evtx"
        self.start_time = datetime.now() - timedelta(hours=24)
        self.end_time = datetime.now()

    def detect(self):
        print("[审计日志] 开始分析...")
        mac = ':'.join(("%012X" % uuid.getnode())[i:i + 2] for i in range(0, 12, 2))
        print(f"[审计日志] 本机 MAC 地址: {mac}")

        user_create_logs = get_log_info(self.path, event_ids=[4720], start_time=self.start_time, end_time=self.end_time)
        print(f"[审计日志] 检测到 {len(user_create_logs)} 个用户创建事件")

        results = []
        for log in user_create_logs:
            username = log.get('TargetUserName', '未知用户')
            print(f"[审计日志] 分析用户: {username}")

            login_logs = get_log_info(self.path, event_ids=[4624], start_time=self.start_time, end_time=self.end_time)
            logout_logs = get_log_info(self.path, event_ids=[4634], start_time=self.start_time, end_time=self.end_time)

            user_login_time = next(
                (to_beijing_time(l['timestamp']) for l in login_logs if l.get('TargetUserName') == username), None)
            user_logout_time = next(
                (to_beijing_time(l['timestamp']) for l in reversed(logout_logs) if l.get('TargetUserName') == username),
                None)

            if user_login_time is None:
                print(f"[审计日志] 用户 {username} 未检测到登录事件，跳过")
                continue

            print(f"[审计日志] 用户 {username} 登录时间: {user_login_time}，注销时间: {user_logout_time or '未知'}")

            activity_logs = get_log_info(self.path,
                                         event_ids = [4723, 4724, 4725, 4726, 4728, 4729, 7045, 1102],
                                         start_time=user_login_time,
                                         end_time=user_logout_time or self.end_time.strftime(
                                             "%Y-%m-%d %H:%M:%S.%f") + " UTC")

            print(f"[审计日志] 用户 {username} 行为事件数量: {len(activity_logs)}")
            actions = []
            for act_log in activity_logs:
                if act_log.get('TargetUserName') != username:
                    continue
                action_name = EVENT_ACTION_MAP.get(act_log.get('event_id'), "未知操作")
                actions.append({
                    "event_id": act_log.get('event_id'),
                    "timestamp": to_beijing_time(act_log.get('timestamp')),
                    "action": action_name,
                    "details": f"{act_log.get('SubjectUserName')}对{act_log.get('TargetUserName')}进行{action_name}"
                })

            result = {
                "mac": mac,
                "username": username,
                "login_time": user_login_time,
                "logoff_time": user_logout_time,
                "actions": actions
            }
            results.append(result)

        print(f"[审计日志] 分析完成，共处理 {len(results)} 个用户")
        results = json.dumps(results, indent=4, ensure_ascii=False)
        print(results)
        return results


class LoginLogDetector:
    def __init__(self, data: dict):
        self.path = r"C:\Windows\System32\winevt\Logs\Security.evtx"
        self.start_time = datetime.now() - timedelta(hours=24)
        self.end_time = datetime.now()
        self.suspicious_users = data.get('suspicious_users', ["test1", "guest", "hacker", "backdoor"])
        self.risk_hours = [0, 1, 2, 3, 4]  # 0点～4点为高风险时间段

    def detect(self):
        print("[登录日志] 开始分析...")
        mac = ':'.join(("%012X" % uuid.getnode())[i:i + 2] for i in range(0, 12, 2))
        print(f"[登录日志] 本机 MAC 地址: {mac}")

        login_logs = get_log_info(self.path, event_ids=[4624], start_time=self.start_time, end_time=self.end_time)
        print(f"[登录日志] 获取登录日志 {len(login_logs)} 条")

        result = []
        self.excluded_users = [u.lower() for u in [
            "local service", "network service", "system",
            "anonymous logon", "fontdriverhost", "winlogon",
            "security", "iis apppool", "iusr", "defaultaccount",
            "guest", "msdtsserver150"
        ]]
        self.excluded_prefixes = ["dwm-", "umfd-"]

        for log in login_logs:
            username = log.get('TargetUserName')
            if username:
                uname = username.lower()
                if uname in self.excluded_users or any(uname.startswith(p) for p in self.excluded_prefixes):
                    continue  # 跳过系统账户
            login_time_str = log.get('timestamp')
            login_time = datetime.strptime(login_time_str, "%Y-%m-%d %H:%M:%S.%f %Z")
            login_time += timedelta(hours=8)
            login_time_str = login_time.strftime("%Y-%m-%d %H:%M:%S")
            is_risk_user = username.lower() in [u.lower() for u in self.suspicious_users]
            is_risk_time = login_time.hour in self.risk_hours

            # ✅ 只记录风险账户或风险时间登录
            if not (is_risk_user or is_risk_time):
                continue

            if (is_risk_user):
                is_risk_user=1
            else:
                is_risk_user=0

            if(is_risk_time):
                is_risk_time=1
            else:
                is_risk_time=0

            print(
                f"[登录日志] [风险] 用户 {username} 登录于 {login_time_str}，风险用户: {is_risk_user}，风险时间: {is_risk_time}")

            result.append({
                "mac": mac,
                "username": username,
                "login_time": login_time_str,
                "is_risk_user": is_risk_user,
                "is_risk_time": is_risk_time
            })

        print(f"[登录日志] 风险记录数: {len(result)}")
        result = json.dumps(result, indent=4, ensure_ascii=False)
        print(result)
        return result



import win32security


class AccountChangeLogDetector:
    def __init__(self, data: dict = None):
        self.path = r"C:\Windows\System32\winevt\Logs\Security.evtx"
        self.start_time = datetime.now() - timedelta(hours=24)
        self.end_time = datetime.now()

        # 支持分析的事件 ID 与初始简要描述
        self.event_action_map = {
            4720: "创建账户",
            4722: "启用账户",
            4723: "尝试更改密码",
            4724: "尝试重置密码",
            4725: "禁用账户",
            4726: "删除账户",
            4738: "成功修改账户",
            4728: "添加到安全组",
            4729: "从安全组中移除",
            4732: "添加到本地组",
            4733: "从本地组中移除",
        }

    def sid_to_name(self, sid_string):
        try:
            sid_obj = win32security.ConvertStringSidToSid(sid_string)
            name, domain, _ = win32security.LookupAccountSid(None, sid_obj)
            return f"{name}"
        except Exception:
            return sid_string  # 返回原始 SID 以防解析失败

    def detect(self):
        print("[账号变更日志] 开始分析...")
        mac = ':'.join(("%012X" % uuid.getnode())[i:i + 2] for i in range(0, 12, 2))
        print(f"[账号变更日志] 本机 MAC 地址: {mac}")

        event_ids = list(self.event_action_map.keys())
        logs = get_log_info(self.path, event_ids=event_ids, start_time=self.start_time, end_time=self.end_time)
        print(f"[账号变更日志] 共获取 {len(logs)} 条事件")

        result = []

        for log in logs:
            event_id = int(log.get("event_id", 0))
            timestamp = log.get("timestamp")
            timestamp = to_beijing_time(timestamp)

            operator_user = log.get("SubjectUserName", "")
            target_user = log.get("TargetUserName", "") or log.get("SamAccountName", "") or "-"

            # 默认描述
            action_desc = ""

            if event_id == 4738:
                changes = []
                if log.get("PasswordLastSet") and "%%" not in log["PasswordLastSet"]:
                    changes.append("更新了密码")
                if log.get("DisplayName") and "%%" not in log["DisplayName"]:
                    changes.append("修改了显示名称")
                if log.get("HomeDirectory") and "%%" not in log["HomeDirectory"]:
                    changes.append("修改了主目录")
                if log.get("PrimaryGroupId"):
                    changes.append("修改了主组")
                if log.get("OldUacValue") != log.get("NewUacValue"):
                    new = log.get("NewUacValue", "")
                    if new == "0x11":
                        changes.append("将账户禁用")
                    elif new == "0x10":
                        changes.append("将账户启用")
                    else:
                        changes.append("修改了账户控制标志")

                if changes:
                    action_desc = f"{operator_user} 成功修改了 {target_user} 的账户信息：" + "、".join(changes)
                else:
                    action_desc = f"{operator_user} 成功修改了 {target_user} 的账户信息"

            elif event_id in [4728, 4729, 4732, 4733]:
                group_name = log.get("TargetUserName") or self.sid_to_name(log.get("TargetSid", "未知组"))
                member_sid = log.get("MemberSid")
                member_name = log.get("MemberName")
                member = self.sid_to_name(member_sid) if member_sid else (member_name or "未知成员")
                group_action = self.event_action_map[event_id]

                if event_id in [4728, 4732]:
                    print(group_name)
                    if group_name=="None":
                        group_name = "Users"
                    action_desc = f"{operator_user} 将 {member} 添加到了组 {group_name}"
                    target_user = member
                else:
                    action_desc = f"{operator_user} 将 {member} 从组 {group_name} 中移除"
                    target_user = member

            else:
                default_action = self.event_action_map.get(event_id, "执行了未知操作")
                if default_action in ["创建账户", "启用账户", "禁用账户", "删除账户"]:
                    action_desc = f"{operator_user} {default_action} {target_user}"
                elif default_action in ["尝试重置密码", "尝试更改密码"]:
                    action_desc = f"{operator_user} 对 {target_user} {default_action}"
                else:
                    action_desc = f"{operator_user} {default_action} 了 {target_user}"

            print(f"[账号变更日志] {timestamp}: {action_desc}")

            result.append({
                "event_id": event_id,
                "timestamp": timestamp,
                "action": action_desc,
                "target_user": target_user,
                "operator_user": operator_user
            })

        print(f"[账号变更日志] 分析完成，共记录 {len(result)} 条")

        data = {
            "mac": mac,
            "actions": result
        }
        data=json.dumps(data, indent=4, ensure_ascii=False)
        print(data)
        return data
