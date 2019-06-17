package com.example.demo.utils;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * 加解密工具类：包括RSA、AES
 *
 * @Author: litu[li_tu@suixingpay.com]
 * @Date: 17:56 2019/5/20
 **/
public abstract class CryptoUtil {
    private static Logger LOGGER = Logger.getLogger(CryptoUtil.class);

    /**
     * 数字签名函数入口
     *
     * @param plainBytes    待签名明文字节数组
     * @param privateKey    签名使用私钥
     * @param signAlgorithm 签名算法
     * @return 签名后的字节数组
     */
    public static byte[] digitalSign(byte[] plainBytes, PrivateKey privateKey, String signAlgorithm) {
        byte[] signBytes = null;
        try {
            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initSign(privateKey);
            signature.update(plainBytes);
            signBytes = signature.sign();

            return signBytes;
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("数字签名时没有[%s]此类算法", e);
        } catch (InvalidKeyException e) {
            LOGGER.error("数字签名时私钥无效", e);
        } catch (SignatureException e) {
            LOGGER.error("数字签名时出现异常", e);
        }
        return signBytes;
    }

    /**
     * 验证数字签名函数入口
     *
     * @param plainBytes    待验签明文字节数组
     * @param signBytes     待验签签名后字节数组
     * @param publicKey     验签使用公钥
     * @param signAlgorithm 签名算法
     * @return 验签是否通过
     */
    public static boolean verifyDigitalSign(byte[] plainBytes, byte[] signBytes, PublicKey publicKey,
            String signAlgorithm) {
        boolean isValid = false;
        try {
            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initVerify(publicKey);
            signature.update(plainBytes);
            isValid = signature.verify(signBytes);
            return isValid;
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("验证数字签名时没有[%s]此类算法", e);
        } catch (InvalidKeyException e) {
            LOGGER.error("验证数字签名时公钥无效", e);
        } catch (SignatureException e) {
            LOGGER.error("验证数字签名时出现异常", e);
        }
        return isValid;
    }

    /**
     * 获取RSA公钥对象
     *
     * @param filePath     RSA公钥路径
     * @param fileSuffix   RSA公钥名称，决定编码类型
     * @param keyAlgorithm 密钥算法
     * @return RSA公钥对象
     */
    public static PublicKey getRSAPublicKeyByFileSuffix(String filePath, String fileSuffix, String keyAlgorithm) {
        InputStream in = null;
        BufferedReader br = null;
        String keyType = "";
        PublicKey pubKey = null;
        if ("crt".equalsIgnoreCase(fileSuffix) || "txt".equalsIgnoreCase(fileSuffix)) {
            keyType = "X.509";
        } else if ("pem".equalsIgnoreCase(fileSuffix)) {
            keyType = "PKCS12";
        } else {
            keyType = "PKCS12";
        }
        try {
            in = new FileInputStream(filePath);
            if ("X.509".equals(keyType)) {
                CertificateFactory factory = CertificateFactory.getInstance(keyType);
                Certificate cert = factory.generateCertificate(in);
                pubKey = cert.getPublicKey();
            } else if ("PKCS12".equals(keyType)) {
                br = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String readLine = null;
                while ((readLine = br.readLine()) != null) {
                    if (readLine.charAt(0) == '-') {
                        continue;
                    } else {
                        sb.append(readLine);
                        sb.append('\r');
                    }
                }
                X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(Base64.decodeBase64(sb.toString()));
                KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
                pubKey = keyFactory.generatePublic(pubX509);
            }

            return pubKey;
        } catch (FileNotFoundException e) {
            LOGGER.error("公钥路径文件不存在", e);
        } catch (CertificateException e) {
            LOGGER.error("生成证书文件错误", e);
        } catch (IOException e) {
            LOGGER.error("读取公钥异常", e);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("生成密钥工厂时没有[%s]此类算法", e);
        } catch (InvalidKeySpecException e) {
            LOGGER.error("生成公钥对象异常", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                LOGGER.error("生成公钥对象异常", e);
            }
        }
        return pubKey;
    }

    /**
     * RSA加密
     *
     * @param plainBytes      明文字节数组
     * @param publicKey       公钥
     * @param keyLength       密钥bit长度
     * @param reserveSize     padding填充字节数，预留11字节
     * @param cipherAlgorithm 加解密算法，一般为RSA/ECB/PKCS1Padding
     * @return 加密后字节数组，不经base64编码
     */
    public static byte[] RSAEncrypt(byte[] plainBytes, PublicKey publicKey, int keyLength, int reserveSize,
            String cipherAlgorithm) {
        int keyByteSize = keyLength / 8; // 密钥字节数
        ByteArrayOutputStream outbuf = null;
        int encryptBlockSize = keyByteSize - reserveSize; // 加密块大小=密钥字节数-padding填充字节数
        int nBlock = plainBytes.length / encryptBlockSize; // 计算分段加密的block数，向上取整
        if ((plainBytes.length % encryptBlockSize) != 0) { // 余数非0，block数再加1
            nBlock += 1;
        }

        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            // 输出buffer，大小为nBlock个keyByteSize
            outbuf = new ByteArrayOutputStream(nBlock * keyByteSize);
            // 分段加密
            for (int offset = 0; offset < plainBytes.length; offset += encryptBlockSize) {
                int inputLen = plainBytes.length - offset;
                if (inputLen > encryptBlockSize) {
                    inputLen = encryptBlockSize;
                }

                // 得到分段加密结果
                byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
                // 追加结果到输出buffer中
                outbuf.write(encryptedBlock);
            }

            outbuf.flush();
            outbuf.close();
            return outbuf.toByteArray();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("没有[%s]此类加密算法", e);
        } catch (NoSuchPaddingException e) {
            LOGGER.error("没有[%s]此类填充模式", e);
        } catch (InvalidKeyException e) {
            LOGGER.error("无效密钥");
        } catch (IllegalBlockSizeException e) {
            LOGGER.error("加密块大小不合法");
        } catch (BadPaddingException e) {
            LOGGER.error("错误填充模式");
        } catch (IOException e) {
            LOGGER.error("字节输出流异常");
        } finally {
            if (outbuf != null) {
                try {
                    outbuf.close();
                } catch (IOException e) {
                    LOGGER.error("字节输出流异常", e);
                }
            }
        }
        return outbuf.toByteArray();
    }

    /**
     * RSA解密
     *
     * @param encryptedBytes  加密后字节数组
     * @param privateKey      私钥
     * @param keyLength       密钥bit长度
     * @param reserveSize     padding填充字节数，预留11字节
     * @param cipherAlgorithm 加解密算法，一般为RSA/ECB/PKCS1Padding
     * @return 解密后字节数组，不经base64编码
     */
    public static byte[] RSADecrypt(byte[] encryptedBytes, PrivateKey privateKey, int keyLength, int reserveSize,
            String cipherAlgorithm) {
        int keyByteSize = keyLength / 8; // 密钥字节数
        ByteArrayOutputStream outbuf = null;
        int decryptBlockSize = keyByteSize - reserveSize; // 解密块大小=密钥字节数-padding填充字节数
        int nBlock = encryptedBytes.length / keyByteSize; // 计算分段解密的block数，理论上能整除

        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            // 输出buffer，大小为nBlock个decryptBlockSize
            outbuf = new ByteArrayOutputStream(nBlock * decryptBlockSize);
            // 分段解密
            for (int offset = 0; offset < encryptedBytes.length; offset += keyByteSize) {
                // block大小: decryptBlock 或 剩余字节数
                int inputLen = encryptedBytes.length - offset;
                if (inputLen > keyByteSize) {
                    inputLen = keyByteSize;
                }

                // 得到分段解密结果
                byte[] decryptedBlock = cipher.doFinal(encryptedBytes, offset, inputLen);
                // 追加结果到输出buffer中
                outbuf.write(decryptedBlock);
            }

            outbuf.flush();
            outbuf.close();
            return outbuf.toByteArray();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("没有[%s]此类解密算法", e);
        } catch (NoSuchPaddingException e) {
            LOGGER.error("没有[%s]此类填充模式", e);
        } catch (InvalidKeyException e) {
            LOGGER.error("无效密钥", e);
        } catch (IllegalBlockSizeException e) {
            LOGGER.error("解密块大小不合法", e);
        } catch (BadPaddingException e) {
            LOGGER.error("错误填充模式", e);
        } catch (IOException e) {
            LOGGER.error("字节输出流异常", e);
        } finally {
            if (outbuf != null) {
                try {
                    outbuf.close();
                } catch (IOException e) {
                    LOGGER.error("字节输出流异常", e);
                }
            }
        }
        return outbuf.toByteArray();
    }

    /**
     * AES加密
     *
     * @param plainBytes      明文字节数组
     * @param keyBytes        密钥字节数组
     * @param keyAlgorithm    密钥算法
     * @param cipherAlgorithm 加解密算法
     * @param IV              随机向量
     * @return 加密后字节数组，不经base64编码
     */
    public static byte[] AESEncrypt(byte[] plainBytes, byte[] keyBytes, String keyAlgorithm, String cipherAlgorithm,
            String IV) {
        byte[] encryptedBytes = null;
        try {
            // AES密钥长度为128bit、192bit、256bit，默认为128bit
            if (keyBytes.length % 8 != 0 || keyBytes.length < 16 || keyBytes.length > 32) {
                LOGGER.error("AES密钥长度不合法");
            }

            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            SecretKey secretKey = new SecretKeySpec(keyBytes, keyAlgorithm);
            if (StringUtils.isEmpty(IV)) {
                IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            }

            encryptedBytes = cipher.doFinal(plainBytes);

            return encryptedBytes;
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("没有[%s]此类加密算法", e);
        } catch (NoSuchPaddingException e) {
            LOGGER.error("没有[%s]此类填充模式", e);
        } catch (InvalidKeyException e) {
            LOGGER.error("无效密钥", e);
        } catch (InvalidAlgorithmParameterException e) {
            LOGGER.error("无效密钥参数", e);
        } catch (BadPaddingException e) {
            LOGGER.error("错误填充模式", e);
        } catch (IllegalBlockSizeException e) {
            LOGGER.error("加密块大小不合法", e);
        }
        return encryptedBytes;
    }

    /**
     * AES解密
     *
     * @param encryptedBytes  密文字节数组，不经base64编码
     * @param keyBytes        密钥字节数组
     * @param keyAlgorithm    密钥算法
     * @param cipherAlgorithm 加解密算法
     * @param IV              随机向量
     * @return 解密后字节数组
     */
    public static byte[] AESDecrypt(byte[] encryptedBytes, byte[] keyBytes, String keyAlgorithm, String cipherAlgorithm,
            String IV) {
        byte[] decryptedBytes = null;
        try {
            // AES密钥长度为128bit、192bit、256bit，默认为128bit
            if (keyBytes.length % 8 != 0 || keyBytes.length < 16 || keyBytes.length > 32) {
                LOGGER.error("AES密钥长度不合法");
            }

            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            SecretKey secretKey = new SecretKeySpec(keyBytes, keyAlgorithm);
            if (IV != null && StringUtils.isEmpty(IV)) {
                IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
                cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
            }

            decryptedBytes = cipher.doFinal(encryptedBytes);

            return decryptedBytes;
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("没有[%s]此类加密算法", e);
        } catch (NoSuchPaddingException e) {
            LOGGER.error("没有[%s]此类填充模式", e);
        } catch (InvalidKeyException e) {
            LOGGER.error("无效密钥", e);
        } catch (InvalidAlgorithmParameterException e) {
            LOGGER.error("无效密钥参数", e);
        } catch (BadPaddingException e) {
            LOGGER.error("错误填充模式", e);
        } catch (IllegalBlockSizeException e) {
            LOGGER.error("解密块大小不合法", e);
        }
        return decryptedBytes;
    }

}
