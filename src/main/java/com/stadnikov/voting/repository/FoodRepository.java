package com.stadnikov.voting.repository;

import com.stadnikov.voting.model.Food;
import com.stadnikov.voting.model.Restaurant;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends BaseRepository<Food> {

    @Query("select restaurant from Restaurant restaurant left join fetch restaurant.food food " +
            "where restaurant.id = :rid AND food.date = CURRENT_DATE()")
    Optional<Restaurant> getRestaurantWithTodayFood(int rid);

    @Query("select restaurant from Restaurant restaurant left join fetch restaurant.food food " +
            "where food.date = CURRENT_DATE()")
    List<Restaurant> getRestaurantsWithTodayFood();

    @Modifying
    @Transactional
    @Query("delete from Food food where food.restaurant.id = :rid AND food.date = CURRENT_DATE()")
    void deleteToday(int rid);
}
