package com.stadnikov.voting.web.vote;

import com.stadnikov.voting.model.Vote;
import com.stadnikov.voting.util.VoteUtil;
import com.stadnikov.voting.web.AuthUser;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserVoteController extends AbstractVoteController {
    static final String REST_URL = "/api/restaurants";

    @CacheEvict(value = { "votes_today", "vote_by_userid" }, key = "#authUser.id")
    @PostMapping(value = "/{rid}/vote")
    public ResponseEntity<Vote> voteFor(@PathVariable int rid, @AuthenticationPrincipal AuthUser authUser) {
        log.info("voteFor rid = {} user = {}", rid, authUser);
        HttpStatus httpStatus = HttpStatus.NOT_MODIFIED;
        if (VoteUtil.isOkToVote(dateUtil)) {
            Vote vote = repository.getTodayByUserId(authUser.id());
            if (vote != null) {
                vote.setDateTime(LocalDateTime.now());
                vote.setRestaurant(restaurantRepository.getById(rid));
            } else {
                vote = new Vote(authUser.getUser(), LocalDateTime.now(), restaurantRepository.getById(rid));
            }
            repository.save(vote);
            httpStatus = HttpStatus.CREATED;
        }
        return ResponseEntity.status(httpStatus).build();
    }
}