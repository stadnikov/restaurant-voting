package com.stadnikov.voting.repository;

import com.stadnikov.voting.model.Restaurant;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Cacheable("restaurants")
    default List<Restaurant> getAll() {
        return findAll();
    }

    @CacheEvict(value = { "restaurant", "restaurants" }, key = "#restaurant.id")
    @Transactional
    default Restaurant saveOrUpdate(Restaurant restaurant) {
        return save(restaurant);
    }

    @Cacheable(value = "restaurant", key = "#rid")
    default Restaurant getById(int rid) {
        return getExisted(rid);
    }

    @CacheEvict(value = { "restaurant", "restaurants" }, key = "#rid")
    @Transactional
    default void deleteById(int rid) {
        deleteExisted(rid);
    }
}
