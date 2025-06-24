import sqlite3

connection = sqlite3.connect('tpp.db')
cursor = connection.cursor()
cursor.execute('''
CREATE TABLE IF NOT EXISTS log_rules (
    id INTEGER PRIMARY KEY AUTOINCREMENT, -- 自增主键
    platform TEXT NOT NULL,              -- 适用平台
    event_id TEXT DEFAULT NULL,          -- 事件ID或匹配字段
    risk_level INTEGER DEFAULT NULL,     -- 1~10表示风险等级
    description TEXT DEFAULT NULL        -- 规则描述
);
''')