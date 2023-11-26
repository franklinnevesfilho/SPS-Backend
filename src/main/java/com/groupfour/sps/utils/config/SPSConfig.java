package com.groupfour.sps.utils.config;

import com.groupfour.sps.services.RegistrationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@EnableAsync
@EnableScheduling
@Configuration
public class SPSConfig {

    public final RegistrationTokenService registrationTokenUtil;

    @Scheduled(fixedDelay = 2, timeUnit = TimeUnit.HOURS)
    public void deleteExpiredRegistrations(){
        registrationTokenUtil.getAll().forEach(token ->{
            if(token.isExpired()) registrationTokenUtil.delete(token);
        });

        log.info("Deleted Expired Registration Tokens");
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void deleteExpired2FA(){
        registrationTokenUtil.getAll().forEach(token ->{
            if(token.isExpired()) registrationTokenUtil.delete(token);
        });

        log.info("Deleted Expired Registration Tokens");
    }

}
