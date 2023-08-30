package com.stadnikov.voting.web.vote;

import com.stadnikov.voting.repository.VoteRepository;
import com.stadnikov.voting.to.VoteTo;
import com.stadnikov.voting.util.DateUtil;
import com.stadnikov.voting.web.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import static com.stadnikov.voting.util.VoteUtil.createTo;
import static com.stadnikov.voting.web.user.UserTestData.USER_ID;
import static com.stadnikov.voting.web.user.UserTestData.USER_MAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = UserVoteController.REST_URL + '/';

    private static final int RID = 3;

    private static final int ANOTHER_RID = 4;

    private static final int BAD_RID = 0;

    DateUtil dateUtil;

    @Autowired
    VoteRepository voteRepository;

    @BeforeEach
    void setup() {
        dateUtil = new DateUtil();
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteNewAfterEleven() throws Exception {
        Field field = dateUtil.getClass().getDeclaredField("hourToTest");
        field.set(dateUtil, 15);
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + RID + "/vote"))
                .andDo(print())
                .andExpect(status().isNotModified());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteNewBeforeEleven() throws Exception {
        Field field = dateUtil.getClass().getDeclaredField("hourToTest");
        field.set(dateUtil, 10);
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + RID + "/vote"))
                .andDo(print())
                .andExpect(status().isCreated());
        assertEquals(new VoteTo(USER_ID, LocalDateTime.now(), RID),
                createTo(voteRepository.getTodayByUserId(USER_ID)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteAgainBeforeEleven() throws Exception {
        Field field = dateUtil.getClass().getDeclaredField("hourToTest");
        field.set(dateUtil, 10);
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + RID + "/vote"))
                .andDo(print())
                .andExpect(status().isCreated());
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + ANOTHER_RID + "/vote"))
                .andDo(print())
                .andExpect(status().isCreated());
        assertEquals(new VoteTo(USER_ID, LocalDateTime.now(), ANOTHER_RID),
                createTo(voteRepository.getTodayByUserId(USER_ID)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteAgainAfterOkVoteAfterEleven() throws Exception {
        Field field = dateUtil.getClass().getDeclaredField("hourToTest");
        field.set(dateUtil, 10);
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + RID + "/vote"))
                .andDo(print())
                .andExpect(status().isCreated());
        field.set(dateUtil, 11);
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + ANOTHER_RID + "/vote"))
                .andDo(print())
                .andExpect(status().isNotModified());
        assertEquals(new VoteTo(USER_ID, LocalDateTime.now(), RID),
                createTo(voteRepository.getTodayByUserId(USER_ID)));
    }


    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteNewBadRid() throws Exception {
        Field field = dateUtil.getClass().getDeclaredField("hourToTest");
        field.set(dateUtil, 10);
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + BAD_RID + "/vote"))
                .andDo(print())
                .andExpect(status().isNotFound());
        field.set(dateUtil, 12);
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + BAD_RID + "/vote"))
                .andDo(print())
                .andExpect(status().isNotModified());
    }

    @Test
    void voteNewUnauthorized() throws Exception {
        Field field = dateUtil.getClass().getDeclaredField("hourToTest");
        field.set(dateUtil, 10);
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + BAD_RID + "/vote"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
        field.set(dateUtil, 12);
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + BAD_RID + "/vote"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}