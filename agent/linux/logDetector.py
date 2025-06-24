import json
class AuditLogDetector:
    def __init__(self, data):
        pass

    def detect(self):
        return json.dumps([])

AccountChangeLogDetector = AuditLogDetector
LoginLogDetector = AuditLogDetector