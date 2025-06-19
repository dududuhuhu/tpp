# coding=utf-8
from evtx import PyEvtxParser
import re
import html
from xml.dom import minidom
from datetime import datetime

from evtx import PyEvtxParser
import re
import html
from xml.dom import minidom
from datetime import datetime, timedelta

EVENT_ACTION_MAP = {
    4624: "成功登录",
    4625: "登录失败",
    4634: "注销",
    4647: "注销（重启或关机）",
    4720: "用户创建",
    4722: "启用用户帐户",
    4723: "用户更改密码",
    4724: "创建安全组",
    4726: "删除用户",
    4728: "添加到组",
    4738: "设置用户密码",
    1074: "关机/重启（含原因）",
    6005: "系统正常启动",
    6006: "系统关闭",
    6008: "非正常关机",
    6009: "系统重启",
    6013: "系统升级重启",
    7036: "服务状态更改",
    7040: "监视者通知操作",
    7045: "安装服务",
    1102: "清除日志"
}



def get_log_info(event_path, event_ids=None, start_time=None, end_time=None):
    """
    获取指定路径下日志文件的内容，支持按事件ID和时间范围过滤
    :param event_path: evtx日志路径
    :param event_ids: 事件ID列表
    :param start_time: datetime类型
    :param end_time: datetime类型
    :return: list[dict]
    """
    parser = PyEvtxParser(event_path)
    pattern = re.compile(r'<EventID>(\d+)</EventID>')
    event_list = []

    for record in parser.records():
        xml_data = record['data']
        res = re.findall(pattern, xml_data)
        if not res:
            continue
        event_id = int(res[0])
        timestamp = record['timestamp']
        # 保证 start_time 和 end_time 是 datetime 类型
        if isinstance(start_time, str):
            try:
                start_time = datetime.strptime(start_time.replace(" UTC", ""), "%Y-%m-%d %H:%M:%S.%f")
            except ValueError:
                start_time = datetime.strptime(start_time.replace(" UTC", ""), "%Y-%m-%d %H:%M:%S")

        if isinstance(end_time, str):
            try:
                end_time = datetime.strptime(end_time.replace(" UTC", ""), "%Y-%m-%d %H:%M:%S.%f")
            except ValueError:
                end_time = datetime.strptime(end_time.replace(" UTC", ""), "%Y-%m-%d %H:%M:%S")
        # 兼容字符串和datetime类型的timestamp
        if isinstance(timestamp, str):
            try:
                timestamp = timestamp.replace(" UTC", "")
                timestamp = datetime.strptime(timestamp, "%Y-%m-%d %H:%M:%S.%f")
            except ValueError:
                try:
                    timestamp = datetime.strptime(timestamp, "%Y-%m-%d %H:%M:%S")
                except ValueError:
                    continue

        # debug 查看是否过滤掉了时间
        # print(f"[DEBUG] Parsed timestamp: {timestamp}")

        # 时间范围过滤
        if start_time and timestamp < start_time:
            continue
        if end_time and timestamp > end_time:
            continue

        # 事件ID过滤
        if event_ids is None or event_id in event_ids:
            r = {'event_id': event_id,  'timestamp': timestamp.strftime("%Y-%m-%d %H:%M:%S.%f") + " UTC"}
            try:
                xml_doc = minidom.parseString(xml_data)
                data_nodes = xml_doc.getElementsByTagName('Data')
                for d in data_nodes:
                    name = d.getAttribute('Name')
                    value = html.unescape(d.firstChild.data) if d.firstChild else ""
                    r[name] = value
            except Exception:
                pass
            event_list.append(r)

    return event_list


def audit_user_activity(event_path, start_time, end_time):
    print("\n🛡️ [审计日志] 用户创建与活动分析：")
    #用户创建为
    user_create_logs = get_log_info(event_path, event_ids=[4720], start_time=start_time, end_time=end_time)
    results = []
    for log in user_create_logs:
        username = log.get('TargetUserName', '未知用户')
        print(f"\n发现新用户创建：{username}，时间：{log['timestamp']}")

        login_logs = get_log_info(event_path, event_ids=[4624], start_time=start_time, end_time=end_time)
        logout_logs = get_log_info(event_path, event_ids=[4634], start_time=start_time, end_time=end_time)

        user_login_time = next((l['timestamp'] for l in login_logs if l.get('TargetUserName') == username), None)
        user_logout_time = next(
            (l['timestamp'] for l in reversed(logout_logs) if l.get('TargetUserName') == username),
            None
        )
        # 如果注销时间为none，则设为end_time
        if user_logout_time is None:
            user_logout_time = end_time
        print(f"登录时间：{user_login_time}")
        print(f"注销时间：{user_logout_time}")
        #只审计日志中targetusername为username的记录activity_logs
        # 返回格式,results示例
        #这里运行需要新建一个用户，并执行登录，修改密码，注销的操作
        """
[{'username': 'test1', 'login_time': '2025-06-18 12:26:59.162429 UTC', 'logoff_time': '2025-06-18 12:42:52.873882 UTC', 'actions': [{'event_id': 4724, 'timestamp': '2025-06-18 12:36:14.985602 UTC', 'action': '创建安全组', 'details': 'prejudice对test1进行创建安全组', 'logs': {'event_id': 4724, 'timestamp': '2025-06-18 12:36:14.985602 UTC', 'TargetUserName': 'test1', 'TargetDomainName': 'LAPTOP-9PM0A5ML', 'TargetSid': 'S-1-5-21-897860091-700114118-385596113-1008', 'SubjectUserSid': 'S-1-5-21-897860091-700114118-385596113-1001', 'SubjectUserName': 'prejudice', 'SubjectDomainName': 'LAPTOP-9PM0A5ML', 'SubjectLogonId': '0x21c7ddf'}}]}]
        """
        user_activity = {
            "username": username,
            "login_time": user_login_time,
            "logoff_time": user_logout_time,
            "actions": []
        }

        # 若有登录注销时间，则收集其在此期间的活动日志

        if user_login_time and user_logout_time:
            activity_logs = get_log_info(event_path,
                                         event_ids=[7045, 1102, 4728, 4724],
                                         start_time=user_login_time,
                                         end_time=user_logout_time)
            print(f"在该登录期间内发现 {len(activity_logs)} 条活动记录")
            for act_log in activity_logs:
                if act_log.get('TargetUserName') != username:
                    continue  # 跳过不是该用户的操作
                action = EVENT_ACTION_MAP.get(act_log.get('event_id'), "未知操作")
                user_activity["actions"].append({
                    "event_id": act_log.get('event_id'),
                    "timestamp": act_log.get('timestamp'),
                    "action":action,
                    "details": f"{act_log.get('SubjectUserName')}对{act_log.get('TargetUserName')}进行{action}",
                    "logs": act_log
                })
        else:
            print("未发现该用户的登录或注销记录")
        results.append(user_activity)
    # 返回所有的新建账户的信息
    return results

"""
登录日志
1. 重点看 危险账号的登录情况
2. 重点看 “阴间人” 在非正常时段进行登录的
"""

def analyze_login_logs(event_path, suspicious_users=None, start_time=None, end_time=None, mac_address='unknown'):
    """
    返回登录风险日志列表（JSON格式）
    """
    results = []

    login_logs = get_log_info(event_path, event_ids=[4624], start_time=start_time, end_time=end_time)

    for log in login_logs:
        username = log.get('TargetUserName', '').lower()
        timestamp = log['timestamp']
        if isinstance(timestamp, str):
            try:
                timestamp = datetime.strptime(timestamp.replace(" UTC", ""), "%Y-%m-%d %H:%M:%S.%f")
            except:
                continue

        is_risk_user = suspicious_users and username in suspicious_users
        is_risk_time = timestamp.hour < 6 or timestamp.hour > 22

        if is_risk_user or is_risk_time:
            result = {
                "username": username,
                "login_time": timestamp.strftime("%Y-%m-%d %H:%M:%S"),
                "is_risk_user": bool(is_risk_user),
                "is_risk_time": bool(is_risk_time)
            }
            results.append(result)

    return results

"""
专门审计账号变更相关日志
1. 授权
2. 改密码之类的
"""
def audit_account_changes(event_path, start_time=None, end_time=None):
    """
    审计账号变更操作，返回包含事件信息的JSON结构
    """
    change_ids = [4723, 4724, 4728, 4738, 4722, 4726]
    logs = get_log_info(event_path, event_ids=change_ids, start_time=start_time, end_time=end_time)

    result = {"actions": []}

    for log in logs:
        event_id = log['event_id']
        timestamp = log['timestamp']
        target_user = log.get("TargetUserName", "未知")
        operator_user = log.get("SubjectUserName", "未知")
        action = EVENT_ACTION_MAP.get(log.get('event_id'), "未知操作")
        action_desc = f"{log.get('SubjectUserName')}对{log.get('TargetUserName')}进行{action}",

        result["actions"].append({
            "event_id": event_id,
            "timestamp": timestamp.strftime("%Y-%m-%d %H:%M:%S") if isinstance(timestamp, datetime) else str(timestamp),
            "action": action_desc,
            "target_user": target_user,
            "operator_user": operator_user
        })

    return result



if __name__ == '__main__':
    from datetime import datetime, timedelta

    end_time = datetime.now()
    start_time = end_time - timedelta(hours=24)
    path = r"C:\Windows\System32\winevt\Logs\Security.evtx"
    suspicious_accounts = ["test1", "guest", "hacker", "backdoor"]

    #审计日志
    results = audit_user_activity(path, start_time, end_time)
    print(results)

    # 登录日志
    resutls1 = analyze_login_logs(path, suspicious_users=suspicious_accounts, start_time=start_time,
                       end_time=end_time)
    print(resutls1)
    # 账号变更日志
    results2 = audit_account_changes(path, start_time, end_time)
    print(results2)
