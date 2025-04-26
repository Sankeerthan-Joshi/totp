package main.java.com.sankeerthan.totp;

import com.sankeerthan.totp.TotpConfig;

public class TotpVerifier {
    private final TotpGenerator generator;
    private final TotpConfig config;

    public TotpVerifier(TotpGenerator generator) {
        this.generator = generator;
        this.config = generator.getConfig();
    }

    public boolean verify(String secret, String otp) throws Exception {
        long currentStep = generator.getTimeProvider().getCurrentTimeMillis() / 1000 / config.getPeriodSeconds();

        for (int i = -config.getAllowedWindow(); i <= config.getAllowedWindow(); i++) {
            String candidate = generator.generateTotpForTime(secret, currentStep + i);
            if (candidate.equals(otp)) {
                return true;
            }
        }
        return false;
    }
}