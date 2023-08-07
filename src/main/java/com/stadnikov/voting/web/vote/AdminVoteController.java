package com.stadnikov.voting.web.vote;

import com.stadnikov.voting.to.VoteTo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static com.stadnikov.voting.util.VoteUtil.createTos;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteController extends AbstractVoteController {
    static final String REST_URL = "/api/admin/restaurants/votes";

    @GetMapping("/{date}")
    public List<VoteTo> get(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return createTos(super.repository.getVotesByDate(date));
    }

    @GetMapping
    public List<VoteTo> getToday() {
        return createTos(super.repository.getTodayVotes());
    }
}
