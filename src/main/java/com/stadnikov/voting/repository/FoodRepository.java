package com.stadnikov.voting.repository;

import com.stadnikov.voting.model.Food;
import com.stadnikov.voting.model.Restaurant;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface FoodRepository extends BaseRepository<Food> {

    @Cacheable(value = "restaurant_today_food", key = "#rid")
    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.food f WHERE r.id = :rid AND f.date = CURRENT_DATE()")
    Optional<Restaurant> getRestaurantWithTodayFood(int rid);

    @Cacheable("restaurants_today_food")
    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.food food WHERE food.date = CURRENT_DATE()")
    List<Restaurant> getRestaurantsWithTodayFood();

    @Transactional
    @Modifying
    @CacheEvict(value = { "restaurant_today_food", "restaurants_today_food" }, key = "#rid")
    @Query("DELETE FROM Food f WHERE f.restaurant.id = :rid AND f.date = CURRENT_DATE()")
    void deleteToday(int rid);
}
