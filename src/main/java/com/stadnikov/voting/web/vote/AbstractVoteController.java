package com.stadnikov.voting.web.vote;

import com.stadnikov.voting.model.Vote;
import com.stadnikov.voting.repository.RestaurantRepository;
import com.stadnikov.voting.repository.VoteRepository;
import com.stadnikov.voting.util.DateUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractVoteController {
    protected final Logger log = getLogger(getClass());

    @Autowired
    protected DateUtil dateUtil;

    @Autowired
    protected VoteRepository repository;

    @Autowired
    RestaurantRepository restaurantRepository;

    public Vote get(int id) {
        log.info("get {}", id);
        return repository.getExisted(id);
    }
}