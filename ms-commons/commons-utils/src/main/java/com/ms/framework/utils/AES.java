package com.ms.framework.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Arrays;

/**
 * Created by mark.zhu on 2016/2/4.
 */
public class AES {
    /**
     * 密钥算法
     */
    public static final String KEY_ALGORITHM = "AES";

    /**
     * 加密/解密算法/工作模式/填充方式
     * JAVA6 支持PKCS5PADDING填充方式
     * Bouncy castle支持PKCS7Padding填充方式
     */
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * <p>生成密钥，java6只支持56位密钥，bouncycastle支持64位密钥</p>
     * <p>美国出口管制的要求，美国政府为了保证国家安全从而限制向部分国家出口先进技术，AES-256加密技术就位列其中</p>
     * <p>如果使用192或256位的密钥，可以使用无政策限制文件(Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files)</p>
     * <p>JDK7的无政策限制文件下载：http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html</p>
     * @param length 密钥长度:128/192/256位
     * @return byte[] 二进制密钥
     */
    public static byte[] initkey(int length) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        kg.init(length);
        SecretKey secretKey = kg.generateKey();
        //获取二进制密钥编码形式
        return secretKey.getEncoded();
    }

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return Key 密钥
     */
    public static Key toKey(byte[] key) throws Exception {
        //实例化DES密钥
        //生成密钥
        SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
        return secretKey;
    }

    /**
     * 加密数据
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密后的数据
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        //还原密钥
        Key k = toKey(key);
        /**
         * 实例化
         * 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现
         * Cipher.getInstance(CIPHER_ALGORITHM,"BC")
         */
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);
        //执行操作
        return cipher.doFinal(data);
    }

    /**
     * 解密数据
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密后的数据
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        //欢迎密钥
        Key k = toKey(key);
        /**
         * 实例化
         * 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现
         * Cipher.getInstance(CIPHER_ALGORITHM,"BC")
         */
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        //执行操作
        return cipher.doFinal(data);
    }
    public static void main(String[] args) throws Exception {
        String str="AES";
        System.out.println("原文："+str);
        //初始化密钥
        byte[] key=AES.initkey(128);
        String strKey = Base64.encodeBase64String(key);
        System.out.println("密钥："+ strKey);
        System.out.println(Arrays.equals(key,Base64.decodeBase64(strKey)));
        System.out.println(Base64.decodeBase64(strKey).length);
        //加密数据
        byte[] data=AES.encrypt(str.getBytes(), key);
        System.out.println("加密后："+Base64.encodeBase64String(data));
        //解密数据
        data=AES.decrypt(data, key);
        System.out.println("解密后："+new String(data));
    }

}
