from utils.crypto.src.asymmetric import SignKeyPair
import json
import base64
import requests
import uuid
import re

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

def translate_bytes_to_str(data:bytes) -> str:
    return base64.b64encode(data).decode('utf-8')

def translate_str_to_bytes(data:str) -> bytes:
    return base64.b64decode(data.encode('utf-8'))

def send_message(signer, path, mac, message):
    url = 'http://localhost:8080'
    sig = translate_bytes_to_str(signer.sign(message.encode('utf-8')))
    data = {
        'mac':mac,
        'message': message,
        'sig': sig
    }
    return requests.post(url + path, json=data)

def main():
    s = SignKeyPair()
    s.generate()
    pub = extract_public_key(s.get_pem_pub())
    print("pub is ", pub)
    message = json.dumps({'pub':pub})
    mac = ':'.join(("%012X" % uuid.getnode())[i:i + 2] for i in range(0, 12, 2))
    response = send_message(s, '/test/testValidateAndAddUserPemPublicKey', mac, message)
    server_s = SignKeyPair()
    server_s.load_pub('-----BEGIN PUBLIC KEY-----\nMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEhqAtQ5IZqhrUo/7W1K5NTZA6y5DUPVGMxdVtGi6Z4WXGv5NwAaz4T5pFs3rOBEz2S5dvKBn7PJ5NeSWd8mNijA==\n-----END PUBLIC KEY-----\n')
    data = json.loads(response.text)
    print(response.text)
    if server_s.verify(data['message'].encode('utf-8'),
                    translate_str_to_bytes(data['sig'])):
        print("验证成功")
    else:
        print("验证失败")
    message = json.dumps({"key1":"value1", "key2": "value2"})
    response = send_message(s, '/test/testVerifySign', mac, message)
    data = json.loads(response.text)
    print(response.text)
    if server_s.verify(data['message'].encode('utf-8'),
                    translate_str_to_bytes(data['sig'])):
        print("验证成功")
    else:
        print("验证失败")

if __name__ == '__main__':
    main()
