package com.stadnikov.voting.web.menu;

import com.stadnikov.voting.model.Food;
import com.stadnikov.voting.model.Restaurant;
import com.stadnikov.voting.repository.FoodRepository;
import com.stadnikov.voting.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractFoodController {
    protected final Logger log = getLogger(getClass());

    @Autowired
    protected FoodRepository repository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @GetMapping("/menu")
    //get all restaurants with their food for today
    public List<Restaurant> getAllTodayFood() {
        log.info("getAllTodayFood");
        return repository.getRestaurantsWithTodayFood();
    }

    @GetMapping("/{rid}/menu")
    //get all food for today and restid=id
    public Optional<Restaurant> getRestaurantTodayFood(@PathVariable int rid) {
        log.info("getRestaurantTodayFood rid = {}", rid);
        return repository.getRestaurantWithTodayFood(rid);
    }

    public Food get(int id) {
        log.info("get {}", id);
        return repository.getExisted(id);
    }

    public void delete(int id) {
        log.info("delete id = {}", id);
        repository.deleteExisted(id);
    }

    public void deleteToday(int rid) {
        log.info("deleteToday rid = {}", rid);
        repository.deleteToday(rid);
    }
}
