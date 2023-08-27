package com.stadnikov.voting.web.vote;

import com.stadnikov.voting.util.DateUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@Primary
class MockDateUtil extends DateUtil {
    @Override
    @Bean("MockDateUtil")
    public DateUtil getDateUtil() {
        return new MockDateUtil();
    }

    @Override
    public int getCurrentHour() {
        return hourToTest;
    }
}
