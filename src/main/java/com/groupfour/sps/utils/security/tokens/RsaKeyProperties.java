package com.groupfour.sps.utils.security.tokens;

import com.groupfour.sps.utils.security.tokens.KeyGeneratorUtil;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @author Franklin Neves
 */
@Data
@Component
public class RsaKeyProperties {
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    @Builder
    public RsaKeyProperties(){
        KeyPair pair = KeyGeneratorUtil.generateRsaKey();
        this.publicKey = (RSAPublicKey) pair.getPublic();
        this.privateKey = (RSAPrivateKey) pair.getPrivate();
    }
}
