package com.stadnikov.voting.web.menu;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.stadnikov.voting.error.NotFoundException;
import com.stadnikov.voting.model.Food;
import com.stadnikov.voting.repository.FoodRepository;
import com.stadnikov.voting.util.JsonUtil;
import com.stadnikov.voting.web.AbstractControllerTest;
import com.stadnikov.voting.web.restaurant.RestaurantTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.stadnikov.voting.web.user.UserTestData.ADMIN_MAIL;
import static com.stadnikov.voting.web.user.UserTestData.USER_MAIL;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminFoodControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/api/admin/restaurants";
    private static final String SLASH_MENU = "/menu";
    private static final String SLASH_RID = "/3";
    private static final String SLASH_RID_WITH_MEALS = "/2";
    private static final String SLASH_BAD_RID = "/0";
    private static final String SLASH_BAD_ID = "/0";
    private static final String SLASH_FOOD5_ID = "/5";
    private static final String SLASH_FOOD6_ID = "/6";

    @Autowired
    FoodRepository foodRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        List<Food> foodList = List.of(
                new Food(null, "New Food", RestaurantTestData.RESTAURANT_3, LocalDate.now(), 99));
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + SLASH_RID + SLASH_MENU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(foodList)))
                .andExpect(status().isCreated());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<Food> created = mapper.readValue(
                action.andReturn().getResponse().getContentAsString(), new TypeReference<>() {
                });
        int newId = created.get(0).id();
        FoodTestData.FOOD_MATCHER.assertMatch(created, foodList);
        FoodTestData.FOOD_MATCHER.assertMatch(List.of(foodRepository.getExisted(newId)), foodList);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocationMoreThanOneItem() throws Exception {
        List<Food> foodList = List.of(
                new Food(null, "New Food", RestaurantTestData.RESTAURANT_3, LocalDate.now(), 99),
                new Food(null, "New Food2", RestaurantTestData.RESTAURANT_3, LocalDate.now(), 199));
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + SLASH_RID + SLASH_MENU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(foodList)))
                .andExpect(status().isCreated());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<Food> created = mapper.readValue(
                action.andReturn().getResponse().getContentAsString(), new TypeReference<>() {
                });
        List<Food> repositoryFood = created.stream().map(f -> foodRepository.getExisted(f.getId())).toList();

        FoodTestData.FOOD_MATCHER.assertMatch(created, foodList);
        FoodTestData.FOOD_MATCHER.assertMatch(repositoryFood, foodList);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocationDuplicate() throws Exception {
//       Existed value ('HAMBURGER', 3, '2023-02-01', 400);
        List<Food> foodList = List.of(
                new Food(null, "HAMBURGER", RestaurantTestData.RESTAURANT_3, LocalDate.of(2023, 2, 1), 99));
        perform(MockMvcRequestBuilders.post(REST_URL + SLASH_RID + SLASH_MENU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(foodList)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocationWithBadFood() throws Exception {
        List<Food> foodList = List.of(new Food(null, null, null, null, null));
        perform(MockMvcRequestBuilders.post(REST_URL + SLASH_RID + SLASH_MENU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(foodList)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocationWithBadRid() throws Exception {
        List<Food> foodList = List.of(
                new Food(null, "New Food", RestaurantTestData.RESTAURANT_3, LocalDate.now(), 99));
        perform(MockMvcRequestBuilders.post(REST_URL + SLASH_BAD_RID + SLASH_MENU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(foodList)))
                .andExpect(status().isNotFound());
    }

    @Test
    void createWithLocationNotAuthorized() throws Exception {
        List<Food> foodList = List.of(
                new Food(null, "New Food", RestaurantTestData.RESTAURANT_3, LocalDate.now(), 99));
        perform(MockMvcRequestBuilders.post(REST_URL + SLASH_RID + SLASH_MENU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(foodList)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createWithLocationWithBadUserRole() throws Exception {
        List<Food> foodList = List.of(
                new Food(null, "New Food", RestaurantTestData.RESTAURANT_3, LocalDate.now(), 99));
        perform(MockMvcRequestBuilders.post(REST_URL + SLASH_RID + SLASH_MENU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(foodList)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Food updatedFood = new Food(null, "New Food", RestaurantTestData.RESTAURANT_3, LocalDate.now(), 99);
        perform(MockMvcRequestBuilders.put(REST_URL + SLASH_RID + SLASH_MENU + SLASH_FOOD5_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedFood)))
                .andDo(print())
                .andExpect(status().isNoContent());
        FoodTestData.FOOD_MATCHER.assertMatch(foodRepository.getExisted(5), updatedFood);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateBadFood() throws Exception {
        Food updatedFood = new Food(null, null, null, null, null);
        perform(MockMvcRequestBuilders.put(REST_URL + SLASH_RID + SLASH_MENU + SLASH_FOOD5_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedFood)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateBadRid() throws Exception {
        Food updatedFood = new Food(null, "New Food", RestaurantTestData.RESTAURANT_3, LocalDate.now(), 99);
        perform(MockMvcRequestBuilders.put(REST_URL + SLASH_BAD_RID + SLASH_MENU + SLASH_FOOD5_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedFood)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateBadId() throws Exception {
        Food updatedFood = new Food(null, "New Food", RestaurantTestData.RESTAURANT_3, LocalDate.now(), 99);
        perform(MockMvcRequestBuilders.put(REST_URL + SLASH_RID + SLASH_MENU + SLASH_BAD_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedFood)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void updateNotAuthorized() throws Exception {
        Food updatedFood = new Food(null, "New Food", RestaurantTestData.RESTAURANT_3, LocalDate.now(), 99);
        perform(MockMvcRequestBuilders.put(REST_URL + SLASH_RID + SLASH_MENU + SLASH_FOOD5_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedFood)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateWithBadUserRole() throws Exception {
        Food updatedFood = new Food(null, "New Food", RestaurantTestData.RESTAURANT_3, LocalDate.now(), 99);
        perform(MockMvcRequestBuilders.put(REST_URL + SLASH_RID + SLASH_MENU + SLASH_FOOD5_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedFood)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + SLASH_RID + SLASH_MENU + SLASH_FOOD6_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> foodRepository.getExisted(6));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteBadRid() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + SLASH_BAD_RID + SLASH_MENU + SLASH_FOOD6_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteBadId() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + SLASH_RID + SLASH_MENU + SLASH_BAD_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteNotAuthorized() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + SLASH_RID + SLASH_MENU + SLASH_FOOD6_ID))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteWithBadUserRole() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + SLASH_RID + SLASH_MENU + SLASH_FOOD6_ID))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteTodayFoodForRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + SLASH_RID_WITH_MEALS + SLASH_MENU))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> foodRepository.getExisted(2));
        assertThrows(NotFoundException.class, () -> foodRepository.getExisted(5));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteTodayFoodForRestaurantWithBadRid() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + SLASH_BAD_RID + SLASH_MENU))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTodayFoodForRestaurantNotAuthorized() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + SLASH_RID_WITH_MEALS + SLASH_MENU))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteTodayFoodForRestaurantWithBadUserRole() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + SLASH_RID_WITH_MEALS + SLASH_MENU))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}