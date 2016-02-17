package com.ms.admin.shiro.security;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

/**
 * 重写{@link org.apache.shiro.crypto.hash.DefaultHashService},不使用HashRequest
 * Created by mark.zhu on 2015/12/8.
 */
public class DefaultHashService {
    /**
     * The MessageDigest name of the hash algorithm to use for computing hashes.
     */
    private String algorithmName;

    /**
     * The 'public' part of the hash salt.
     */
    private String publicSalt;

    /**
     * The number of hash iterations to perform when computing hashes.
     */
    private int iterations;

    private boolean storedCredentialsHexEncoded;

    public String computeHash(String source, ByteSource privateSalt) {
        ByteSource salt = combine(privateSalt, ByteSource.Util.bytes(publicSalt.getBytes()));
        Hash computed = new SimpleHash(algorithmName, source, salt, iterations);
        if (isStoredCredentialsHexEncoded()) {
            return new String(Hex.encode(computed.getBytes()));
        } else {
            return new String(Base64.encode(computed.getBytes()));
        }
    }

    /**
     * Combines the specified 'private' salt bytes with the specified additional extra bytes to use as the
     * total salt during hash computation.  {@code privateSaltBytes} will be {@code null} }if no private salt has been
     * configured.
     *
     * @param privateSalt the (possibly {@code null}) 'private' salt to combine with the specified extra bytes
     * @param publicSalt  the extra bytes to use in addition to the given private salt.
     * @return a combination of the specified private salt bytes and extra bytes that will be used as the total
     * salt during hash computation.
     */
    protected ByteSource combine(ByteSource privateSalt, ByteSource publicSalt) {

        byte[] privateSaltBytes = privateSalt != null ? privateSalt.getBytes() : null;
        int privateSaltLength = privateSaltBytes != null ? privateSaltBytes.length : 0;

        byte[] publicSaltBytes = publicSalt != null ? publicSalt.getBytes() : null;
        int extraBytesLength = publicSaltBytes != null ? publicSaltBytes.length : 0;

        int length = privateSaltLength + extraBytesLength;

        if (length <= 0) {
            return null;
        }

        byte[] combined = new byte[length];

        int i = 0;
        for (int j = 0; j < privateSaltLength; j++) {
            assert privateSaltBytes != null;
            combined[i++] = privateSaltBytes[j];
        }
        for (int j = 0; j < extraBytesLength; j++) {
            assert publicSaltBytes != null;
            combined[i++] = publicSaltBytes[j];
        }

        return ByteSource.Util.bytes(combined);
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public String getPublicSalt() {
        return publicSalt;
    }

    public void setPublicSalt(String publicSalt) {
        this.publicSalt = publicSalt;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public boolean isStoredCredentialsHexEncoded() {
        return storedCredentialsHexEncoded;
    }

    public void setStoredCredentialsHexEncoded(boolean storedCredentialsHexEncoded) {
        this.storedCredentialsHexEncoded = storedCredentialsHexEncoded;
    }
}
