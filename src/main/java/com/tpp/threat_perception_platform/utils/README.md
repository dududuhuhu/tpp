# 加密工具类使用说明

本文档介绍了如何使用加密工具类进行签名、验证、加密和解密操作。

## 概述

加密工具类包括以下几个主要组件：

1. `KeyPair` - 密钥对基类，提供密钥对的基本操作
2. `SignKeyPair` - 签名密钥对，使用ECC（椭圆曲线加密）实现
3. `EncryptKeyPair` - 加密密钥对，使用RSA实现
4. `Encryption` - 对称加密类，使用AES（CBC模式）实现
5. `CryptoUtils` - 提供简单接口使用上述类的工具类

这些类的设计遵循了"使用ECC进行签名，使用RSA进行加密，使用AES进行对称加密"的原则。

## 使用示例

### 签名和验证

```java
// 生成签名密钥对
SignKeyPair signKeyPair = CryptoUtils.generateSignKeyPair();

// 获取PEM格式的公钥和私钥
String privateKey = signKeyPair.getPemPrivateKey();
String publicKey = signKeyPair.getPemPublicKey();

// 使用私钥签名数据
String message = "需要签名的消息";
String signature = CryptoUtils.sign(signKeyPair, message);

// 使用公钥验证签名
boolean isValid = CryptoUtils.verify(signKeyPair, message, signature);
```

### 加密和解密

```java
// 生成加密密钥对
EncryptKeyPair encryptKeyPair = CryptoUtils.generateEncryptKeyPair();

// 获取PEM格式的公钥和私钥
String privateKey = encryptKeyPair.getPemPrivateKey();
String publicKey = encryptKeyPair.getPemPublicKey();

// 使用公钥加密数据
String message = "需要加密的消息";
String encrypted = CryptoUtils.encrypt(encryptKeyPair, message);

// 使用私钥解密数据
String decrypted = CryptoUtils.decrypt(encryptKeyPair, encrypted);
```

### 从已有密钥创建密钥对

```java
// 从PEM格式的私钥和公钥创建签名密钥对
SignKeyPair signKeyPair = CryptoUtils.createSignKeyPair(privateKey, publicKey);

// 从PEM格式的私钥和公钥创建加密密钥对
EncryptKeyPair encryptKeyPair = CryptoUtils.createEncryptKeyPair(privateKey, publicKey);
```

### 使用ECDH生成共享密钥

```java
// 生成两个签名密钥对
SignKeyPair keyPair1 = CryptoUtils.generateSignKeyPair();
SignKeyPair keyPair2 = CryptoUtils.generateSignKeyPair();

// 交换公钥并生成共享AES密钥
String publicKey1 = keyPair1.getPemPublicKey();
String publicKey2 = keyPair2.getPemPublicKey();

// 在一方生成AES密钥
String aesKey1 = CryptoUtils.generateSharedAESKey(keyPair1, publicKey2);

// 在另一方生成相同的AES密钥
String aesKey2 = CryptoUtils.generateSharedAESKey(keyPair2, publicKey1);

// aesKey1和aesKey2是相同的，可以用于对称加密
```

### 对称加密（AES）

```java
// 创建对称加密对象
Encryption encryption = new Encryption();

// 生成256位AES密钥
encryption.generate();

// 或者加载已有的密钥
byte[] key = new byte[32]; // 32字节 = 256位
// ... 初始化密钥 ...
encryption.loadKey(key);

// 获取当前密钥
byte[] currentKey = encryption.getKey();

// 加密数据（自动生成IV）
String message = "需要加密的消息";
byte[] plaintext = message.getBytes(StandardCharsets.UTF_8);
Encryption.EncryptionResult result = encryption.encrypt(plaintext);

// 获取密文和IV
byte[] ciphertext = result.getCiphertext();
byte[] iv = result.getIv();

// 解密数据
byte[] decrypted = encryption.decrypt(ciphertext, iv);
String decryptedMessage = new String(decrypted, StandardCharsets.UTF_8);

// 使用指定的IV加密数据
byte[] customIv = new byte[16]; // 16字节 = 128位
// ... 初始化IV ...
Encryption.EncryptionResult resultWithCustomIv = encryption.encrypt(plaintext, customIv);
```

### 对称加密（AES）

`Encryption`类提供了AES对称加密功能，使用CBC模式和PKCS7填充。主要特点：

- 支持256位密钥（32字节）
- 支持自动生成或手动指定IV（16字节）
- 返回加密结果和IV的组合对象

使用方法包括：生成密钥、加载密钥、加密数据（自动或手动指定IV）和解密数据。

## 注意事项

1. 签名密钥对使用ECC（椭圆曲线加密），适用于数字签名和密钥交换
2. 加密密钥对使用RSA，适用于加密和解密
3. 对称加密使用AES（CBC模式），适用于大量数据的高效加密
4. 非对称密钥以PEM格式存储和传输，对称密钥以字节数组形式存储
5. 签名和非对称加密结果以Base64编码返回，对称加密结果以字节数组形式返回
6. 使用ECDH生成的共享密钥可以用于AES对称加密
