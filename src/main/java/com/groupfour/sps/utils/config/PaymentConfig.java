package com.groupfour.sps.utils.config;

import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
/**
 * @author Fuxinyang Chang
 */
@Configuration
public class PaymentConfig {
    @Value("${PAYPAL_ID}")
    private String clientId;
    @Value("${PAYPAL_SECRET}")
    private String clientSecret;
    @Value("${PAYPAL_MODE}")
    private String mode;

    @Bean
    public Map<String, String> paypalSdkConfig(){
        Map<String, String> configMap = new HashMap<>();
        configMap.put("mode", mode);
        return configMap;
    }

    /**
     * @return A connection to the PayPal server
     */
    @Bean
    public APIContext apiContext(){
        APIContext context = new APIContext(clientId, clientSecret, mode, paypalSdkConfig());
        context.setConfigurationMap(paypalSdkConfig());
        return context;
    }
}
