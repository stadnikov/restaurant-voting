package com.stadnikov.voting.web.vote;

import com.stadnikov.voting.model.Vote;
import com.stadnikov.voting.util.VoteUtil;
import com.stadnikov.voting.web.AuthUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserVoteController extends AbstractVoteController {
    static final String REST_URL = "/api/restaurants";

    //vote for restaurant with rid
    @PostMapping(value = "/{rid}/vote")
    public ResponseEntity<Vote> voteFor(@PathVariable int rid, @AuthenticationPrincipal AuthUser authUser) {
        if (VoteUtil.isOkToVote()) {
            Vote vote = repository.getTodayByUserId(authUser.id());
            if (vote != null) {
                vote.setDateTime(LocalDateTime.now());
                vote.setRestaurant(restaurantRepository.getById(rid));
            } else {
                vote = new Vote(authUser.getUser(), LocalDateTime.now(), restaurantRepository.getById(rid));
            }
            //create or update vote
            Vote created = repository.save(vote);
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{rid}/vote")
                    .buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(uriOfNewResource).body(created);
        } else {
            //do nothing because unable to vote now
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }
}
