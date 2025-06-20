from urllib.parse import quote_plus
import re

def get_amqp_url(host, port, user, password, virtual_host):
    return f'amqp://{user}:{quote_plus(password)}@{host}:{port}/{quote_plus(virtual_host)}'

def extract_public_key(pem_string):
    """
    从标准 PEM 格式的公钥字符串中提取公钥部分
    :param pem_string: 包含 PEM 格式公钥的字符串
    :return: 公钥部分的字符串
    """
    # 使用正则表达式匹配 PEM 公钥部分
    match = re.search(r"-----BEGIN PUBLIC KEY-----(.*?)-----END PUBLIC KEY-----", pem_string, re.DOTALL)
    if match:
        # 提取匹配的内容并去除多余的空格或换行
        return match.group(1).strip()
    else:
        raise ValueError("未找到有效的 PEM 公钥部分")