package com.stadnikov.voting.web.restaurant;

import com.stadnikov.voting.model.Restaurant;
import com.stadnikov.voting.web.MatcherFactory;
import com.stadnikov.voting.web.menu.FoodTestData;

import java.util.List;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "food");
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER_WITH_FOOD =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "food.id", "food.restaurant", "food.date");


    public static final Restaurant RESTAURANT_1 = new Restaurant(1, "VIP Restaurant", FoodTestData.RESTAURANT_1_FOOD);
    public static final Restaurant RESTAURANT_2 = new Restaurant(2, "Cheap Restaurant", FoodTestData.RESTAURANT_2_FOOD);
    public static final Restaurant RESTAURANT_3 = new Restaurant(3, "Italian Restaurant", null);
    public static final Restaurant RESTAURANT_4 = new Restaurant(4, "Chinese Restaurant", null);

    public static final List<Restaurant> RESTAURANTS = List.of(RESTAURANT_2, RESTAURANT_4, RESTAURANT_3, RESTAURANT_1);

    public static final List<Restaurant> RESTAURANTS_WITH_TODAY_FOOD = List.of(RESTAURANT_2, RESTAURANT_1);
}
