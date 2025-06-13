from threading import Lock
import json
import pika
from pika.exchange_type import ExchangeType
from mq.service import Service
from utils import logger

class Publisher(Service):
    """
    发布者类，用于向 RabbitMQ 发布消息
    """

    def __init__(self, exchange, amqp_url, exchange_type=ExchangeType.direct):
        # 初始化父类并设置发布确认相关的状态值
        super().__init__(exchange, amqp_url, exchange_type)
        self._deliveries = None       # 保存已发送但尚未确认的消息 delivery_tag
        self._acked = None            # 成功确认的消息数量
        self._nacked = None           # 被拒绝的消息数量
        self._message_number = None   # 已发送消息的序号
        self._publish_lock: Lock = Lock()  # 发布操作加锁，避免并发问题

    def _app_on_bindok(self, frame, userdata):
        """
        队列绑定成功后的回调
        """
        print('bindok')
        self._start_publishing()

    def _start_publishing(self):
        """
        启动发布逻辑，开启消息确认机制
        """
        logger.info("开始发布消息")
        self._enable_delivery_confirmations()

    def _app_on_start(self):
        """
        初始化统计数据，在连接建立后调用
        """
        self._deliveries = {}
        self._acked = 0
        self._nacked = 0
        self._message_number = 0

    def _enable_delivery_confirmations(self):
        """
        启用消息确认功能，设置回调函数
        """
        logger.info("启用消息发布确认机制")
        self._channel.confirm_delivery(self._on_delivery_confirmation)

    def _on_delivery_confirmation(self, method_frame):
        """
        处理消息确认的回调，无论是 ack 还是 nack
        :param method_frame: pika 传入的确认帧，包含 delivery_tag 和是否 multiple
        """
        confirmation_type = method_frame.method.NAME.split('.')[1].lower()
        ack_multiple = method_frame.method.multiple
        delivery_tag = method_frame.method.delivery_tag

        logger.info('收到 %s 确认, delivery_tag: %i (multiple: %s)',
                    confirmation_type, delivery_tag, ack_multiple)

        if confirmation_type == 'ack':
            self._acked += 1
        elif confirmation_type == 'nack':
            self._nacked += 1

        # 删除该 delivery_tag 对应的记录
        if delivery_tag in self._deliveries:
            del self._deliveries[delivery_tag]

        # 如果是批量确认（multiple=True），清理之前所有 delivery_tag
        if ack_multiple:
            for tmp_tag in list(self._deliveries.keys()):
                if tmp_tag <= delivery_tag:
                    self._acked += 1
                    del self._deliveries[tmp_tag]

        logger.info(
            '共发布消息 %i 条, 还有 %i 条待确认, 成功确认 %i 条, 拒绝 %i 条',
            self._message_number,
            len(self._deliveries),
            self._acked,
            self._nacked
        )

    def publish_message(self, routing_key, message: str, hdrs: dict = None):
        """
        发布消息到指定的 routing_key，并附加 headers（可选）

        :param routing_key: 路由键
        :param message: 要发送的消息（字符串形式）
        :param hdrs: 可选的 headers 字典
        """
        with self._publish_lock:
            if self.is_stopped():
                logger.warning("通道未开启或已停止，不能发送消息")
                return

            properties = pika.BasicProperties(
                headers=hdrs,
                content_type='application/json'
            )

            self._channel.basic_publish(
                exchange=self._exchange,
                routing_key=routing_key,
                body=message,  # ✅ 不要再 json.dumps
                properties=properties
            )

            self._message_number += 1
            self._deliveries[self._message_number] = True

            logger.info("已发布消息: %s", message)
