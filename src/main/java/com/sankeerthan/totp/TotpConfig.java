package com.sankeerthan.totp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TotpConfig {
    @Default
    private int periodSeconds = 30;

    @Default
    private int digits = 6;

    @Default
    private String hmacAlgorithm = "HmacSHA1";

    @Default
    private int allowedWindow = 1;

}