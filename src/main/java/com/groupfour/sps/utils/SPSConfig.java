package com.groupfour.sps.utils;

import com.groupfour.sps.services.RegistrationTokenService;
import com.paypal.base.rest.APIContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@EnableAsync
@EnableScheduling
@Configuration
public class SPSConfig {

    @Value("${PAYPAL_ID}")
    private String clientId;
    @Value("${PAYPAL_SECRET}")
    private String clientSecret;
    @Value("${PAYPAL_MODE}")
    private String mode;

    public final RegistrationTokenService registrationTokenUtil;

    @Scheduled(fixedDelay = 2, timeUnit = TimeUnit.HOURS)
    public void deleteExpiredRegistrations(){
        registrationTokenUtil.getAll().forEach(token ->{
            if(token.isExpired()) registrationTokenUtil.delete(token);
        });

        log.info("Deleted Expired Registration Tokens");
    }

    @Bean
    public Map<String, String> paypalSdkConfig(){
        Map<String, String> configMap = new HashMap<>();
        configMap.put("mode", mode);
        return configMap;
    }

    @Bean
    public APIContext apiContext(){
        APIContext context = new APIContext(clientId, clientSecret, mode, paypalSdkConfig());
        context.setConfigurationMap(paypalSdkConfig());
        return context;
    }



}
