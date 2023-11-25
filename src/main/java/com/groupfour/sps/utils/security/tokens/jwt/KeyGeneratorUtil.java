package com.groupfour.sps.utils.security.tokens.jwt;

import lombok.extern.slf4j.Slf4j;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

@Slf4j
public class KeyGeneratorUtil {
    public static KeyPair generateRsaKey(){
        KeyPair keyPair = null;
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            keyPair = generator.generateKeyPair();
        }catch(Exception e){
            log.error("Error generating key pair");
        }
        return keyPair;
    }
}
