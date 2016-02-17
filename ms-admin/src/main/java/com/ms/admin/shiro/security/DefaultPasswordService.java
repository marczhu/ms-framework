package com.ms.admin.shiro.security;

import com.ms.framework.domain.account.User;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 重写{@link org.apache.shiro.authc.credential.DefaultPasswordService} 不支持HashFormat、HashRequest
 * Created by mark.zhu on 2015/12/4.
 */
public class DefaultPasswordService implements PasswordService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPasswordService.class);

    protected static final String DEFAULT_HASH_ALGORITHM = "SHA-256";
    protected static final int DEFAULT_HASH_ITERATIONS = 2;
    protected static final String PUBLIC_SALT = "PBS";

    private DefaultHashService hashService;

    private SecureRandomNumberGenerator randomNumberGenerator;

    public DefaultPasswordService() {
        DefaultHashService hashService = new DefaultHashService();
        hashService.setAlgorithmName(DEFAULT_HASH_ALGORITHM);
        hashService.setIterations(DEFAULT_HASH_ITERATIONS);
        hashService.setPublicSalt(PUBLIC_SALT);
        this.hashService = hashService;
        this.randomNumberGenerator = new SecureRandomNumberGenerator();
    }

    public DefaultPasswordService(DefaultHashService hashService) {
        this.hashService = hashService;
        this.randomNumberGenerator = new SecureRandomNumberGenerator();
    }

    @Override
    public User encryptPassword(String plaintext) throws IllegalArgumentException {
        if (plaintext == null || plaintext.isEmpty()) {
            return null;
        }
        ByteSource privateSalt = randomNumberGenerator.nextBytes();
        String encrypted = computeHash(plaintext, privateSalt);
        User user = new User();
        /**
         * 数据库存储base64编码后的salt
         */
        user.setSalt(privateSalt.toBase64());
        user.setPassword(encrypted);
        return user;
    }

    @Override
    public boolean passwordsMatch(String plaintext, User encrypted) {
        ByteSource plaintextBytes = createByteSource(plaintext);
        if (encrypted == null || encrypted.getPassword() == null || encrypted.getPassword().isEmpty()) {
            return plaintextBytes == null || plaintextBytes.isEmpty();
        } else {
            if (plaintextBytes == null || plaintextBytes.isEmpty()) {
                return false;
            }
        }
        if (encrypted.getSalt() == null) {
            throw new IllegalStateException("salt is null!");
        }
        String computed = computeHash(plaintext, toByteSource(encrypted.getSalt()));
        return encrypted.getPassword().equals(computed);
    }

    protected ByteSource createByteSource(String text) {
        return new SimpleByteSource(text);
    }

    protected String computeHash(String plaintext, ByteSource privateSalt) {
        return substring(hashService.computeHash(plaintext, privateSalt), 32);
    }

    private String substring(String target, int length) {
        if (target == null) {
            return null;
        } else {
            if (target.length() > length) {
                return target.substring(0, length);
            } else {
                return target;
            }
        }
    }

    /**
     * Base64 decode，并转换成ByteSource
     *
     * @param target
     * @return
     */
    private ByteSource toByteSource(String target) {
        byte[] saltBytes = Base64.decode(target);
        return ByteSource.Util.bytes(saltBytes);
    }
}
