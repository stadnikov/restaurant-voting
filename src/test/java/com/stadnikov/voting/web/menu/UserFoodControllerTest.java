package com.stadnikov.voting.web.menu;

import com.stadnikov.voting.web.AbstractControllerTest;
import com.stadnikov.voting.web.restaurant.RestaurantTestData;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.stadnikov.voting.web.user.UserTestData.USER_MAIL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserFoodControllerTest extends AbstractControllerTest {
    private static final String REST_URL = "/api/restaurants";
    private static final String SLASH_MENU = "/menu";
    private static final String SLASH_RID = "/2";
    private static final String SLASH_BAD_RID = "/0";

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllTodayFood() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + SLASH_MENU))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_MATCHER_WITH_FOOD.contentJson(
                        RestaurantTestData.RESTAURANTS_WITH_TODAY_FOOD));
    }

    @Test
    void getAllTodayFoodNotAuthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + SLASH_MENU))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getRestaurantTodayFood() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + SLASH_RID + SLASH_MENU))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_MATCHER_WITH_FOOD.contentJson(RestaurantTestData.RESTAURANT_2));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getRestaurantTodayFoodWithBadRid() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + SLASH_BAD_RID + SLASH_MENU))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void getRestaurantTodayFoodNotAuthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + SLASH_RID + SLASH_MENU))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}