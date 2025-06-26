import sqlite3
from utils.logParser import LogParser
from service import MAC
import json
from db.tpp import get_log_rules
import datetime

class LogDetect(object):
    def __init__(self, log_path:str, logger_config:str='utils/config.yml'):
        """
        初始化日志检测器
        :param log_path: 日志文件路径
        :param rule_db: 规则数据库路径
        """
        self._log_path = log_path
        self._parser = LogParser(logger_config)
        self._time = datetime.datetime.min.replace(tzinfo=datetime.timezone.utc)
    
    def _is_valid_timestamp(self, timestamp: str) -> bool:
        try:
            # 解析时间戳为 offset-aware datetime
            event_time = datetime.datetime.fromisoformat(timestamp)
            # 比较时间戳是否在 self._time 之后
            return event_time >= self._time
        except ValueError:
            # 如果时间戳格式不正确，返回 False
            return False
    
    def _get_obj_events(self) -> dict:
        """
        从规则数据库中获取事件列表
        :return: 事件列表
        """
        try:
            events = {}
            for rule in get_log_rules():
                if rule[2]:
                    events[rule[2]] = [rule[0], rule[3]]  # event_id: [id, risk_level]
        except sqlite3.Error as e:
            print(f"SQLite error: {e}")
            events = {}
        return events
    
    def _parse(self, events:dict) -> list[dict] | None:
        """
        解析日志文件
        :return: 日志记录列表
        """
        results = []
        with open(self._log_path, 'r') as file:
            keys = events.keys()
            for line in file:
                try:
                    parsed = self._parser.parseLine(line)
                    if parsed.get('appname') in keys and self._is_valid_timestamp(parsed.get('timestamp', datetime.datetime.min)):
                        results.append({
                            'mac':MAC,
                            'id':events[parsed.get('appname')][0],
                            'event_id':parsed.get('appname'),
                            'event':line,
                            'event_time':parsed.get('timestamp', ''),
                            'risk_level':events[parsed.get('appname')][1],
                        })
                except Exception as e:
                    print(f"Error parsing line: {e}")
                    continue
        return results
    
    def detect(self):
        """
        执行日志检测
        :return: 检测结果列表
        """
        events = self._get_obj_events()
        if not events:
            print("No events found in the rule database.")
            return None
        
        results = self._parse(events)
        self._time = datetime.datetime.now().replace(tzinfo=datetime.timezone.utc)
        if not results:
            print("No matching events found in the log file.")
            return None
        
        return json.dumps(results, ensure_ascii=False)
