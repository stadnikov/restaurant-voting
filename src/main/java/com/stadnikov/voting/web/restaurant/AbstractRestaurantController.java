package com.stadnikov.voting.web.restaurant;

import com.stadnikov.voting.model.Restaurant;
import com.stadnikov.voting.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractRestaurantController {
    protected final Logger log = getLogger(getClass());

    @Autowired
    protected RestaurantRepository repository;

    public void delete(int rid) {
        log.info("delete rid = {}", rid);
        repository.deleteById(rid);
    }

    @GetMapping("/{rid}")
    public Restaurant get(@PathVariable int rid) {
        log.info("get rid = {}", rid);
        return repository.getById(rid);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("getAll");
        return repository.getAll();
    }
}