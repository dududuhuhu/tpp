from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from cryptography.hazmat.primitives import padding
from cryptography.hazmat.backends import default_backend
import os

# Use AES(CBC mode) for encryption.

class Encryption:
    def __init__(self):
        super().__init__()
        self.key: bytes = None


    def generate(self) -> None:
        self.key = os.urandom(32) # keys are 256 bits.
    

    def load_key(self, key: bytes) -> None:
        if len(key) != 32:
            raise RuntimeError("Invalid key length.")
        
        self.key = key
    

    def get_key(self) -> bytes:
        if self.key:
            return self.key
        else:
            raise RuntimeError("Key absent.")

    def encrypt(self, plaintext: bytes, iv: bytes=None) -> tuple:
        if not iv:
            iv = os.urandom(16) # initialization vectors are 128 bits.
        else:
            if len(iv) != 16:
                raise RuntimeError("Invalid initialization vector length.")
        
        if not self.key:
            raise RuntimeError("Key absent.")
        
        padder = padding.PKCS7(algorithms.AES.block_size).padder()
        padded_data = padder.update(plaintext) + padder.finalize()

        cipher = Cipher(algorithms.AES(self.key), modes.CBC(iv), backend=default_backend())
        encryptor = cipher.encryptor()
        ciphertext = encryptor.update(padded_data) + encryptor.finalize()
        return ciphertext, iv


    def decrypt(self, ciphertext: bytes, iv: bytes) -> bytes:
        if len(iv) != 16:
            raise RuntimeError("Invalid initialization vector length.")
        
        if not self.key:
            raise RuntimeError("Key absent.")
        
        cipher = Cipher(algorithms.AES(self.key), modes.CBC(iv), backend=default_backend())
        decryptor = cipher.decryptor()
        padded_data = decryptor.update(ciphertext) + decryptor.finalize()

        unpadder = padding.PKCS7(algorithms.AES.block_size).unpadder()
        data = unpadder.update(padded_data) + unpadder.finalize()
        return data