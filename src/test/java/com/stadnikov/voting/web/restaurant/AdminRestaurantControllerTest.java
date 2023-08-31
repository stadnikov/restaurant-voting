package com.stadnikov.voting.web.restaurant;

import com.stadnikov.voting.model.Restaurant;
import com.stadnikov.voting.repository.RestaurantRepository;
import com.stadnikov.voting.util.JsonUtil;
import com.stadnikov.voting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.stadnikov.voting.web.user.UserTestData.ADMIN_MAIL;
import static com.stadnikov.voting.web.user.UserTestData.USER_MAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminRestaurantControllerTest extends AbstractControllerTest {

    static final String REST_URL = "/api/admin/restaurants";

    static final String REST_URL_SLASH = REST_URL + "/";

    private static final int RID = 3;

    private static final int BAD_RID = 0;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.RESTAURANT_NEW;
        newRestaurant.setId(null);
        restaurantRepository.saveOrUpdate(newRestaurant);
        List<Restaurant> restaurantList = restaurantRepository.getAll();
        // and remove New Restaurant
        restaurantList.remove(newRestaurant);
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + newRestaurant.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertEquals(restaurantList, restaurantRepository.getAll());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteWithBadRid() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + BAD_RID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + RID))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }


    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteBadUserRole() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + RID))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Restaurant newRestaurant = new Restaurant(null, "New Restaurant", new ArrayList<>());
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated());

        Restaurant created = RestaurantTestData.RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(restaurantRepository.getExisted(newId), newRestaurant);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocationDuplicate() throws Exception {
        Restaurant newRestaurant = new Restaurant(null, "VIP Restaurant", new ArrayList<>());
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocationBadRestaurant() throws Exception {
        Restaurant invalid = new Restaurant(null, null, null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createWithLocationNotAuthorized() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createWithLocationWithBadUserRole() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Restaurant updatedRestaurant = new Restaurant(null, "Updated Restaurant", new ArrayList<>());
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + RID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedRestaurant)))
                .andDo(print())
                .andExpect(status().isNoContent());
        updatedRestaurant.setId(RID);
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(restaurantRepository.getExisted(RID), updatedRestaurant);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = ADMIN_MAIL)
    void updateDuplicate() throws Exception {
        Restaurant updatedRestaurant = new Restaurant(null, "VIP Restaurant", new ArrayList<>());
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + RID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedRestaurant)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateBadRestaurant() throws Exception {
        Restaurant invalid = new Restaurant(null, null, null);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateBadRid() throws Exception {
        Restaurant updatedRestaurant = new Restaurant(4, "Updated Restaurant", new ArrayList<>());
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + BAD_RID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedRestaurant)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateNotAuthorized() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateWithBadUserRole() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}