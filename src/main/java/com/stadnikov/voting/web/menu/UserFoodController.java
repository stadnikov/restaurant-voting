package com.stadnikov.voting.web.menu;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = UserFoodController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserFoodController extends AbstractFoodController {
    static final String REST_URL = "/api/restaurants";
}