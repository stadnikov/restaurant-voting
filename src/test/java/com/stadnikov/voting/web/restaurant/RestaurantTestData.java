package com.stadnikov.voting.web.restaurant;

import com.stadnikov.voting.model.Restaurant;
import com.stadnikov.voting.web.MatcherFactory;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "food");

    public static final Restaurant RESTAURANT_1 = new Restaurant(1, "VIP Restaurant", null);
    public static final Restaurant RESTAURANT_2 = new Restaurant(2, "Cheap Restaurant", null);
    public static final Restaurant RESTAURANT_3 = new Restaurant(3, "Italian Restaurant", null);
    public static final Restaurant RESTAURANT_4 = new Restaurant(4, "Chinese Restaurant", null);

    /*
    VALUES ('VIP Restaurant'),
       ('Cheap Restaurant'),
       ('Italian Restaurant'),
       ('Chinese Restaurant');
     */

}
