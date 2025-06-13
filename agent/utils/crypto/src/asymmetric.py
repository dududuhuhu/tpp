from cryptography.hazmat.primitives.asymmetric import ec, rsa, padding
from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.primitives import serialization
from cryptography.hazmat.backends import default_backend
from abc import ABC, abstractmethod

# Use ECC for signature, RSA for encryption.

__all__ = ["SignKeyPair", "EncryptKeyPair"]


def key_serialize(key, is_pri: bool, encoding=serialization.Encoding.PEM) -> str:
    ret = None
    if is_pri:
        ret = key.private_bytes(
            encoding=encoding,
            format=serialization.PrivateFormat.PKCS8,
            encryption_algorithm=serialization.NoEncryption()
        ).decode('utf-8')
    else:
        ret = key.public_bytes(
            encoding=encoding,
            format=serialization.PublicFormat.SubjectPublicKeyInfo
        ).decode('utf-8')

    return ret


def key_deserialize(key: str, is_pri: bool):
    ret = None
    if is_pri:
        ret = serialization.load_pem_private_key(
            key.encode('utf-8'),
            password=None
        )
    else:
        ret = serialization.load_pem_public_key(key.encode('utf-8'))

    return ret


class KeyPair:
    def __init__(self):
        super().__init__()
        self.pri = None
        self.pub = None
        self.pem_pri = None
        self.pem_pub = None
    
    @abstractmethod
    def generate(self):
        pass
    
    def get_pub(self):
        if self.pub is not None:
            return self.pub
        else:
            raise RuntimeError("Public key absent.")
    
    def get_pri(self):
        if self.pri is not None:
            return self.pri
        else:
            raise RuntimeError("Private key absent.")
    

    def get_pem_pub(self) -> str:
        if self.pem_pub is not None:
            return self.pem_pub
        elif self.pub is not None:
            self.serialize()
            return self.pem_pub
        else:
            raise RuntimeError("Private key absent.")
    
    def get_pem_pri(self) -> str:
        if self.pem_pri is not None:
            return self.pem_pri
        elif self.pri is not None:
            self.serialize()
            return self.pem_pri
        else:
            raise RuntimeError("Private key absent.")
    
    def load_pri(self, key: str) -> None:
        self.pem_pri = key
        self.pri = key_deserialize(key, True)
    
    def load_pub(self, key: str) -> None:
        self.pem_pub = key
        self.pub = key_deserialize(key, False)
    
    def serialize(self) -> None:
        if self.pri:
            self.pem_pri = key_serialize(self.pri, True)
        
        if self.pub:
            self.pem_pub = key_serialize(self.pub, False)


class SignKeyPair(KeyPair):
    def _generate_ecc_key_pair(curve=ec.SECP256R1()):
        private_key = ec.generate_private_key(curve)
        public_key = private_key.public_key()
        return private_key, public_key

    def _sign(key: ec.EllipticCurvePrivateKey, data: bytes, 
            signature_algorithm=ec.ECDSA(hashes.SHA256())) -> bytes:
        signature = key.sign(data, signature_algorithm=signature_algorithm)
        return signature


    def _verify(key: ec.EllipticCurvePublicKey, data:bytes, signature: bytes, 
               signature_algorithm=ec.ECDSA(hashes.SHA256())) -> bool:
        ret = True

        try:
            key.verify(signature, data, signature_algorithm=signature_algorithm)
        except Exception:
            ret = False

        return ret

    def __init__(self):
        super().__init__()

    def generate(self) -> None:
        self.pri, self.pub = SignKeyPair._generate_ecc_key_pair()
    
    def sign(self, data: bytes) -> bytes:
        if self.pri:
            return SignKeyPair._sign(self.pri, data)
        else:
            raise RuntimeError("Private key absent.")
    
    def verify(self, data: bytes, signature: bytes) -> bool:
        if self.pub:
            return SignKeyPair._verify(self.pub, data, signature)
        else:
            raise RuntimeError("Public key absent.")
    
    def generate_aes_key(self, pem_pub_key: str) -> bytes:
        pub_key = key_deserialize(pem_pub_key, False)
        key = self.pri.exchange(ec.ECDH(), pub_key)
        key_hash = hashes.Hash(hashes.SHA256(), backend=default_backend())
        key_hash.update(key)
        return key_hash.finalize()
    
    def generate_pub(self):
        if self.pri and self.pub is None:
            self.pub = self.pri.public_key()
        elif self.pri and self.pub:
            return
        else:
            raise RuntimeError("Private key absent.")



class EncryptKeyPair(KeyPair):
    def _generate_rsa_key_pair():
        private_key = rsa.generate_private_key(
            public_exponent=65537,
            key_size=2048
        )
        public_key = private_key.public_key()
        return private_key, public_key
    

    def _encrypt(key: rsa.RSAPublicKey, plaintext: bytes) -> bytes:
        ciphertext = key.encrypt(
            plaintext,
            padding.OAEP(
                mgf=padding.MGF1(algorithm=hashes.SHA256()),
                algorithm=hashes.SHA256(),
                label=None
            )
        )
        return ciphertext
    

    def _decrypt(key: rsa.RSAPrivateKey, ciphertext: bytes) -> bytes:
        plaintext = key.decrypt(
            ciphertext,
            padding.OAEP(
                mgf=padding.MGF1(algorithm=hashes.SHA256()),
                algorithm=hashes.SHA256(),
                label=None
            )
        )
        return plaintext


    def __init__(self):
        super().__init__()
    

    def generate(self) -> None:
        self.pri, self.pub = EncryptKeyPair._generate_rsa_key_pair()
    

    def encrypt(self, plaintext: bytes) -> bytes:
        if self.pub:
            return EncryptKeyPair._encrypt(self.pub, plaintext)
        else:
            raise RuntimeError("Public key absent.")

    def decrypt(self, ciphertext: bytes):
        if self.pri:
            return EncryptKeyPair._decrypt(self.pri, ciphertext)
        else:
            raise RuntimeError("Private key absent.")