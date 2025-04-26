package main.java.com.sankeerthan.totp;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.sankeerthan.totp.TotpConfig;

public class TotpGenerator {
    private final TotpConfig config;
    private final TimeProvider timeProvider;

    public TotpGenerator(TotpConfig config, TimeProvider timeProvider) {
        this.config = config;
        this.timeProvider = timeProvider;
    }

    public String generateTotp(String base32Secret) throws NoSuchAlgorithmException, InvalidKeyException {
        long timeStep = timeProvider.getCurrentTimeMillis() / 1000 / config.getPeriodSeconds();
        return generateTotpForTime(base32Secret, timeStep);
    }

    public String generateTotpForTime(String base32Secret, long timeStep) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] key = SecretManager.decodeBase32(base32Secret);
        byte[] data = new byte[8];
        for (int i = 7; i >= 0; i--) {
            data[i] = (byte) (timeStep & 0xFF);
            timeStep >>= 8;
        }
        SecretKeySpec signKey = new SecretKeySpec(key, config.getHmacAlgorithm());
        Mac mac = Mac.getInstance(config.getHmacAlgorithm());
        mac.init(signKey);
        byte[] hash = mac.doFinal(data);

        int offset = hash[hash.length - 1] & 0xF;
        int binary = ((hash[offset] & 0x7F) << 24)
                   | ((hash[offset + 1] & 0xFF) << 16)
                   | ((hash[offset + 2] & 0xFF) << 8)
                   | (hash[offset + 3] & 0xFF);

        int otp = binary % (int) Math.pow(10, config.getDigits());
        return String.format("%0" + config.getDigits() + "d", otp);
    }
}