package com.stadnikov.voting.repository;

import com.stadnikov.voting.model.Restaurant;

import java.util.List;

public interface RestaurantRepository extends BaseRepository<Restaurant> {

    default List<Restaurant> getAll() {
        return findAll();
    }

    default Restaurant saveOrUpdate(Restaurant restaurant) {
        return save(restaurant);
    }

    default Restaurant getById(int rid) {
        return getExisted(rid);
    }

    default void deleteById(int rid) {
        deleteExisted(rid);
    }
}
