import socket
from ipaddress import ip_address

# 获取主机的名字
print(socket.gethostname())

# 获取ip地址
s=socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
s.connect(("144.144.144.144",80))
ip_address=s.getsockname()[0]
