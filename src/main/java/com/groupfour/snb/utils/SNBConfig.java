package com.groupfour.snb.utils;

import com.groupfour.snb.utils.security.tokens.RegistrationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
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
@ComponentScan
public class SNBConfig {

    public final RegistrationTokenService registrationTokenUtil;


    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void deleteExpiredRegistrations(){
        registrationTokenUtil.getAll().forEach(token ->{
            if(token.isExpired()) registrationTokenUtil.delete(token);
        });

        log.info("Deleted Expired Registration Tokens");
    }
}
