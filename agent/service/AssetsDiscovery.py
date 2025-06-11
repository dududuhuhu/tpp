from mq import default_mq
import json
import nmap
import socket
import threading
from pojo import default_sysinfo

class AssetsDiscoveryService:
    def __init__(self):
        pass

    def callback(self, channel, deliver, properties, body) -> bool:
        try:
            data = json.loads(body.decode())
            print(f"Received data: {data}")
            # Process the data as needed
            # For example, you might want to log it or send it to another service
            if 'service' in data:
                self.__service_discovery()
        except Exception as e:
            print(f"Error processing message: {e}")
        finally:
            return True

    def __get_ip_address() -> str:
        ip = "127.0.0.1"
        try:
            # 获取当前设备的实际 IP 地址，而不是通过主机名解析
            with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as s:
                s.connect(("114.114.114.114", 80))  # 使用外部地址进行连接以获取本地 IP
                ip = s.getsockname()[0]
        except socket.error:
            pass
        finally:
            return ip
    
    def __service_discovery_thread():
        ip = AssetsDiscoveryService.__get_ip_address()

        nm = nmap.PortScanner()
        nm.scan(hosts=ip, arguments='-sTV')
        state = nm.all_hosts()[0]
        service = []

        if state:
            for host in nm.all_hosts():
                for proto in nm[host].all_protocols():
                    lport = nm[host][proto].keys()
                    for port in lport:
                        service.append({
                            'port': port,
                            'name': nm[host][proto][port]['name'],
                            'state': nm[host][proto][port]['state'],
                            'protocol': proto,
                            'product': nm[host][proto][port].get('product', ''),
                            'version': nm[host][proto][port].get('version', ''),
                            'extrainfo': nm[host][proto][port].get('extrainfo', '')
                        })

        payload = {
            'macAddress' : default_sysinfo.get_mac_address(),
            'service' : service,
        }
        print(f'payload: {json.dumps(payload)}')
        
        default_mq.publish(json.dumps(payload), routing_key='service_discovery')

        
    def __service_discovery(self):
        thread = threading.Thread(target=AssetsDiscoveryService.__service_discovery_thread)
        thread.start()

