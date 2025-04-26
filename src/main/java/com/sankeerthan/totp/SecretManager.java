package main.java.com.sankeerthan.totp;

import org.apache.commons.codec.binary.Base32;


public class SecretManager {
    private static final Base32 base32 = new Base32();

    private SecretManager() {
        // Prevent instantiation
    }

    public static byte[] decodeBase32(String base32Secret) {
        return base32.decode(base32Secret);
    }

    public static String encodeBase32(byte[] bytes) {
        return base32.encodeToString(bytes);
    }
}