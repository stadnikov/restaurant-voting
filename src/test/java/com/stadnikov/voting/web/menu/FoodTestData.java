package com.stadnikov.voting.web.menu;

import com.stadnikov.voting.model.Food;
import com.stadnikov.voting.model.Restaurant;
import com.stadnikov.voting.web.MatcherFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static com.stadnikov.voting.web.restaurant.RestaurantTestData.RESTAURANT_1;
import static com.stadnikov.voting.web.restaurant.RestaurantTestData.RESTAURANT_2;

public class FoodTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_WITH_FOOD_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);

    public static final MatcherFactory.Matcher<Food> FOOD_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Food.class, "id", "date", "restaurant");

    public static final Food FOOD_2_1 = new Food(null, "TEA", RESTAURANT_2, Date.valueOf(LocalDate.now()), 50);
    public static final Food FOOD_2_2 = new Food(null, "TOASTS", RESTAURANT_2, Date.valueOf(LocalDate.now()), 150);
    public static final Food FOOD_1_1 = new Food(null, "FRIED EGGS", RESTAURANT_1, Date.valueOf(LocalDate.now()), 999);

    public static final List<Food> RESTAURANT_1_FOOD = List.of(FOOD_1_1);
    public static final List<Food> RESTAURANT_2_FOOD = List.of(FOOD_2_1, FOOD_2_2);

//    static {
//        RESTAURANT_2.setFood(RESTAURANT_2_FOOD);
//    }
}
