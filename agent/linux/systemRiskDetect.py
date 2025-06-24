import json

class SystemRiskDetectLinux:
    def __init__(self, data):
        self._mac = data['macAddress']
    
    def detect(self):
        return json.dumps([])