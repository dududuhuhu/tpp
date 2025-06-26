import json
class BaselineCheckDetect:

    def __init__(self, data):
        pass

    def detect(self) -> str:
        return json.dumps([])

BaselineHardenDetect = BaselineCheckDetect
