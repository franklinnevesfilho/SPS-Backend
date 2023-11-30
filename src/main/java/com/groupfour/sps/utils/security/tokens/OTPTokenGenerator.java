package com.groupfour.sps.utils.security.tokens;

import java.util.Random;

/**
 * @author Otton Lara
 */
public class OTPTokenGenerator {
    private static final int DEFAULT_TOKEN_SIZE = 6;

    public static String generateToken(){
        StringBuilder token = new StringBuilder();
        for(int i = 0; i < DEFAULT_TOKEN_SIZE; i++){
            token.append(new Random().nextInt(10));
        }
        return token.toString();
    }
}
