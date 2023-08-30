package com.stadnikov.voting.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;

@Configuration
public class DateUtil {
    // for tests changing time
    public static int hourToTest = 11;

    @Bean
    public DateUtil getDateUtil() {
        return new DateUtil();
    }

    public int getCurrentHour() {
        return LocalTime.now().getHour();
    }
}
