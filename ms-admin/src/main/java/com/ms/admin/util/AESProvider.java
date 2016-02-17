package com.ms.admin.util;

import com.ms.framework.utils.AES;
import org.apache.commons.codec.binary.Base64;

/**
 * AES帮助类
 * 加密后的byte[]用Base64编码成明文
 * Created by mark.zhu on 2016/2/5.
 */

public class AESProvider {
    private String securityKey;
    private static final int KEY_LENGTH = 128;

    public AESProvider(String securityKey) {
        this.securityKey = securityKey;
    }

    /**
     * 生成128位密钥，用Base64编码为明文
     *
     * @return
     * @throws Exception
     */
    public static String generateSecurityKey() throws Exception {
        return Base64.encodeBase64String(AES.initkey(KEY_LENGTH));
    }

    /**
     * 加密并编码
     *
     * @param target
     * @return
     * @throws Exception
     */
    public String encrypt(String target) throws Exception {
        byte[] encrypted = AES.encrypt(target.getBytes(), getSecurityKey());
        return Base64.encodeBase64String(encrypted);
    }

    /**
     * 解密
     * @param target
     * @return
     * @throws Exception
     */
    public String decrypt(String target) throws Exception {
        byte[] decoded = Base64.decodeBase64(target.getBytes());
        byte[] decrypted = AES.decrypt(decoded, getSecurityKey());
        return new String(decrypted);
    }

    /**
     * base64 decode为128位(16字节)原始密钥
     * @return
     */
    public byte[] getSecurityKey() {
        return Base64.decodeBase64(securityKey);
    }

    public static void main(String[] args) throws Exception {
//        String securityKey = AESProvider.generateSecurityKey();
        String securityKey = "6i6XnRjkPqB+g3F38bv3Bg==";

        System.out.println("securityKey=" + securityKey);
        AESProvider aesProvider = new AESProvider(securityKey);

        String target = "admin";
        String encrypted = aesProvider.encrypt(target);
        System.out.println("encrypted=" + encrypted);
        String decrypted = aesProvider.decrypt(encrypted);
        System.out.println("decrypted=" + decrypted);
    }
}
