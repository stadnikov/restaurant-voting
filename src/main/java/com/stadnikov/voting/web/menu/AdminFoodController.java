package com.stadnikov.voting.web.menu;

import com.stadnikov.voting.model.Food;
import com.stadnikov.voting.model.Restaurant;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.stadnikov.voting.util.validation.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = AdminFoodController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminFoodController extends AbstractFoodController {

    static final String REST_URL = "/api/admin/restaurants";

    //Post menu for spec. restaurant (a lot of food)
    //post rest/$rid/menu
    @PostMapping(value = "/{rid}/menu",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Food>> createWithLocation(@Valid @RequestBody List<Food> listOfFood,
                                                         @PathVariable int rid) {
        log.info("create {}", listOfFood);
//        checkNew(restaurant);
        Restaurant restaurant = restaurantRepository.getById(rid);
        listOfFood.forEach(food -> food.setRestaurant(restaurant));

        List<Food> created = repository.saveAll(listOfFood);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{rid}")
                .buildAndExpand(rid).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{rid}/menu/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Food food, @PathVariable int id, @PathVariable int rid) {
        log.info("update {} with id={}", food, id);
        assureIdConsistent(food, id);
        Restaurant restaurant = restaurantRepository.getById(rid);
        food.setRestaurant(restaurant);
        repository.save(food);
    }

    @DeleteMapping("/{rid}/menu/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int rid, @PathVariable int id) {
        //TODO check for rid?
        super.delete(id);
    }

    //Delete today menu for specified restaurant
    @Override
    @DeleteMapping("/{rid}/menu")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int rid) {
        super.deleteToday(rid);
    }
}
