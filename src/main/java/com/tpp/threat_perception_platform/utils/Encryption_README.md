# 对称加密（AES）使用说明

本文档介绍了如何使用`Encryption`类进行对称加密操作。

## 概述

`Encryption`类提供了AES对称加密功能，使用CBC模式和PKCS7填充。主要特点：

- 支持256位密钥（32字节）
- 支持自动生成或手动指定IV（16字节）
- 返回加密结果和IV的组合对象

## 使用示例

### 创建对称加密对象

```java
// 创建对称加密对象
Encryption encryption = new Encryption();
```

### 生成密钥

```java
// 生成256位AES密钥
encryption.generate();
```

### 加载已有密钥

```java
// 加载已有的密钥（必须是32字节 = 256位）
byte[] key = new byte[32];
// ... 初始化密钥 ...
encryption.loadKey(key);
```

### 获取当前密钥

```java
// 获取当前密钥
byte[] currentKey = encryption.getKey();
```

### 加密数据（自动生成IV）

```java
// 准备要加密的数据
String message = "需要加密的消息";
byte[] plaintext = message.getBytes(StandardCharsets.UTF_8);

// 加密数据（自动生成IV）
Encryption.EncryptionResult result = encryption.encrypt(plaintext);

// 获取密文和IV
byte[] ciphertext = result.getCiphertext();
byte[] iv = result.getIv();
```

### 使用指定的IV加密数据

```java
// 准备自定义IV（必须是16字节 = 128位）
byte[] customIv = new byte[16];
// ... 初始化IV ...

// 使用指定的IV加密数据
Encryption.EncryptionResult resultWithCustomIv = encryption.encrypt(plaintext, customIv);
```

### 解密数据

```java
// 解密数据
byte[] decrypted = encryption.decrypt(ciphertext, iv);
String decryptedMessage = new String(decrypted, StandardCharsets.UTF_8);
```

## 错误处理

`Encryption`类会在以下情况抛出`RuntimeException`：

1. 密钥长度不是32字节（256位）
2. IV长度不是16字节（128位）
3. 尝试在没有密钥的情况下加密或解密
4. 加密或解密过程中发生错误

## 与其他加密类的集成

`Encryption`类可以与`SignKeyPair`类结合使用，通过ECDH密钥交换生成共享AES密钥：

```java
// 生成两个签名密钥对
SignKeyPair keyPair1 = new SignKeyPair();
SignKeyPair keyPair2 = new SignKeyPair();
keyPair1.generate();
keyPair2.generate();

// 交换公钥
String publicKey1 = keyPair1.getPemPublicKey();
String publicKey2 = keyPair2.getPemPublicKey();

// 生成共享AES密钥
byte[] sharedKey1 = keyPair1.generateAesKey(publicKey2);
byte[] sharedKey2 = keyPair2.generateAesKey(publicKey1);

// 使用共享密钥进行对称加密
Encryption encryption = new Encryption();
encryption.loadKey(sharedKey1);
```

## 安全注意事项

1. 密钥应妥善保管，不应硬编码在代码中
2. IV不需要保密，但每次加密应使用不同的IV
3. 对称加密适用于大量数据的高效加密，但密钥分发是一个挑战
4. 考虑结合非对称加密（如RSA）来安全地分发对称密钥