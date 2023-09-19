package com.groupfour.snb.utils;

import com.groupfour.snb.utils.tokens.RegistrationTokenUtil;
import com.groupfour.snb.utils.tokens.SessionTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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

    public final SessionTokenUtil sessionTokenUtil;
    public final RegistrationTokenUtil registrationTokenUtil;


    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void deleteExpiredRegistrations(){
        registrationTokenUtil.getAll().forEach(token ->{
            if(token.isExpired()) registrationTokenUtil.delete(token);
        });

        log.info("Deleted Expired Registration Tokens");
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void deleteExpiredSessions(){
        sessionTokenUtil.getAll().forEach(token ->{
            if(token.hasExpired()) sessionTokenUtil.delete(token);
        });
        log.info("Deleted Expired Sessions");
    }
}
