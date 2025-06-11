import pika
from typing import Callable
import threading

class RabbitMQ:
    def __init__(self, host="172.17.0.2", port=5672, user="admin", password="hello123456", vhost="my_vhost"):
        self.__host = host
        self.__port = port
        self.__user = user
        self.__password = password
        self.__vhost = vhost
        self.__connection = None
        self.__channel:pika.adapters.blocking_connection.BlockingChannel = None
    
    def __connect(self):
        credentials = pika.PlainCredentials(self.__user, self.__password)
        parameters = pika.ConnectionParameters(host=self.__host, port=self.__port, virtual_host=self.__vhost, credentials=credentials)
        self.__connection = pika.BlockingConnection(parameters)
        self.__channel = self.__connection.channel()
    
    def __channel_is_usable(self):
        return self.__channel is not None and self.__channel.is_open
    
    def publish(self, message, exchange='sysinfo_exchange', routing_key='sysinfo'):
        if not self.__channel_is_usable():
            self.__connect()
        try:
            self.__channel.basic_publish(exchange=exchange, routing_key=routing_key, body=message)
        except Exception as e:
            print(f"Error publishing message: {e}")
            self.__connect()  # Reconnect if there's an error
            self.__channel.basic_publish(exchange=exchange, routing_key=routing_key, body=message)
        
    def register_consume(self, callback:Callable[[pika.channel.Channel, pika.spec.Basic.Deliver, pika.BasicProperties, bytes], None], queue='sysinfo_queue'):
        if not self.__channel_is_usable():
            self.__connect()
        def debug(ch, method, properties, body):
            print(f"Received message: {body.decode()}")
        try:    
            print(f"callback: {callback}")
            self.__channel.basic_consume(queue=queue, on_message_callback=debug, auto_ack=True)
        except Exception as e:
            print(f"Error consuming messages: {e}")
            self.__connect()
            self.__channel.basic_consume(queue=queue, on_message_callback=callback, auto_ack=True)
    
    def __consume_thread(self):
        assert self.__channel_is_usable(), "Channel is not usable, cannot start consuming."
        while True:
            try:
                self.__channel.start_consuming()
            except pika.exceptions.ConnectionClosedByBroker:
                print("Connection closed by broker, reconnecting...")
                self.__connect()
            except pika.exceptions.ConnectionClosed:
                print("Connection closed, reconnecting...")
                self.__connect()
            except Exception as e:
                print(f"Error during consuming: {e}")
                self.__connect()
                break
    
    def start_consuming(self):
        if not self.__channel_is_usable():
            self.__connect()
        consume_thread = threading.Thread(target=self.__consume_thread, daemon=True)
        consume_thread.start()
        print("Started consuming messages in a separate thread.")


