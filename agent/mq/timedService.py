from mq.service import Service
class TimedService:
    def __init__(self, func, startup_delay:float, interval:float, routing_key:str=None):
        self._func = func
        self._startup_delay = startup_delay
        self._interval = interval
        self._routing_key = routing_key
        self._publisher = None
    
    def bind_publisher(self, publisher:Service):
        self._publisher = publisher
    
    def __call__(self):
        """
        调用定时任务函数，并在需要时发布消息。
        """
        if self._routing_key:
            message = self._func()
            if message is not None:
                self._publisher.publish_message(
                    routing_key=self._routing_key,
                    message=message,
                )
        else:
            self._func()
        self._publisher.timed_task_schedule(self._interval, self)
    
    def get_startup_delay(self) -> float:
        return self._startup_delay
    
