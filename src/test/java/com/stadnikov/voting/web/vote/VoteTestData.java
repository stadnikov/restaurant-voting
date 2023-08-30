package com.stadnikov.voting.web.vote;

import com.stadnikov.voting.model.User;
import com.stadnikov.voting.model.Vote;
import com.stadnikov.voting.to.VoteTo;
import com.stadnikov.voting.web.MatcherFactory;
import com.stadnikov.voting.web.restaurant.RestaurantTestData;
import com.stadnikov.voting.web.user.UserTestData;

import java.time.LocalDateTime;
import java.util.List;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "dateTime");
    public static final MatcherFactory.Matcher<VoteTo> VOTETO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class, "dateTime");
    public static final MatcherFactory.Matcher<VoteTo> VOTETO_WITH_DATE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class);

    public static final User TODAY_VOTE_USER_1 = UserTestData.user;
    public static final User TODAY_VOTE_USER_2 = UserTestData.admin;
    public static final User TODAY_VOTE_USER_3 = UserTestData.guest;

    public static final int TODAY_VOTE_1 = 2;
    public static final int TODAY_VOTE_2 = 3;
    public static final int TODAY_VOTE_3 = 1;

    public static final Vote vote_1 = new Vote(TODAY_VOTE_USER_1, LocalDateTime.now(), RestaurantTestData.RESTAURANT_2);
    public static final Vote vote_2 = new Vote(TODAY_VOTE_USER_2, LocalDateTime.now(), RestaurantTestData.RESTAURANT_3);
    public static final Vote vote_3 = new Vote(TODAY_VOTE_USER_3, LocalDateTime.now(), RestaurantTestData.RESTAURANT_1);
    public static final List<Vote> todayVotes = List.of(vote_1, vote_2, vote_3);

    public static final VoteTo voteTo_1 = new VoteTo(TODAY_VOTE_USER_1.id(), LocalDateTime.now(), TODAY_VOTE_1);
    public static final VoteTo voteTo_2 = new VoteTo(TODAY_VOTE_USER_2.id(), LocalDateTime.now(), TODAY_VOTE_2);
    public static final VoteTo voteTo_3 = new VoteTo(TODAY_VOTE_USER_3.id(), LocalDateTime.now(), TODAY_VOTE_3);
    public static final List<VoteTo> todayVoteTos = List.of(voteTo_1, voteTo_2, voteTo_3);


    public static final VoteTo voteToWithDate_1 = new VoteTo(1,LocalDateTime.of(2023, 8, 1, 10, 0), 1);
    public static final VoteTo voteToWithDate_2 = new VoteTo(3,LocalDateTime.of(2023, 8, 1, 9, 0),2 );
    public static final List<VoteTo> voteTosWithDate = List.of(voteToWithDate_2, voteToWithDate_1);

}
