package com.stadnikov.voting.web.vote;

import com.stadnikov.voting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.stadnikov.voting.web.user.UserTestData.ADMIN_MAIL;
import static com.stadnikov.voting.web.user.UserTestData.USER_MAIL;
import static com.stadnikov.voting.web.vote.VoteTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminVoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = AdminVoteController.REST_URL + '/';

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + voteToWithDate_1.getDateTime().toLocalDate()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTETO_WITH_DATE_MATCHER.contentJson(voteTosWithDate));
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + voteToWithDate_1.getDateTime().toLocalDate()))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getBadAuthorizedRole() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + voteToWithDate_1.getDateTime().toLocalDate()))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getWithNoVotesForThatDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + LocalDate.of(1999, 1, 1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTETO_WITH_DATE_MATCHER.contentJson(new ArrayList<>()));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getToday() throws Exception {
        perform(MockMvcRequestBuilders.get(AdminVoteController.REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTETO_MATCHER.contentJson(todayVoteTos));
    }

    @Test
    void getTodayUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(AdminVoteController.REST_URL))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getTodayBadAuthorizedRole() throws Exception {
        perform(MockMvcRequestBuilders.get(AdminVoteController.REST_URL))
                .andExpect(status().isForbidden())
                .andDo(print());
    }
}