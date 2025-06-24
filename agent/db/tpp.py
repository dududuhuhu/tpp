# tpp database management
from db import sqlite3
from threading import Lock

tpp_lock = Lock()

def update_log_rules(rules:list[tuple]):
    with tpp_lock:
        connection = sqlite3.connect('tpp.db')
        cursor = connection.cursor()
        query = """
            INSERT OR REPLACE INTO log_rules (id, platform, event_id, risk_level, description)
            VALUES (?, ?, ?, ?, ?)
            """
        cursor.executemany(query, rules)
        cursor.commit()
        cursor.close()
        connection.close()

def get_log_rules(platform=None, event_id=None, risk_level=None):
    """
    获取日志规则
    :param platform: 适用平台
    :param event_id: 事件ID或匹配字段
    :param risk_level: 风险等级
    :return: 符合条件的日志规则列表
    """
    with tpp_lock:
        connection = sqlite3.connect('tpp.db')
        cursor = connection.cursor()
    
        query = "SELECT * FROM log_rules WHERE 1=1"
        params = []
    
        if platform:
            query += " AND platform=?"
            params.append(platform)
        if event_id:
            query += " AND event_id=?"
            params.append(event_id)
        if risk_level:
            query += " AND risk_level=?"
            params.append(risk_level)
    
        cursor.execute(query, params)
        rules = cursor.fetchall()
    
        cursor.close()
        connection.close()
    
        return rules