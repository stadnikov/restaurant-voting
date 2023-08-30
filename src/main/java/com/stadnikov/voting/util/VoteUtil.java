package com.stadnikov.voting.util;

import com.stadnikov.voting.model.Vote;
import com.stadnikov.voting.to.VoteTo;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class VoteUtil {
    private static final int hourOfStopVoting = 11;

    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.getUser().getId(), vote.getDateTime(), vote.getRestaurant().getId());
    }

    public static List<VoteTo> createTos(List<Vote> votes) {
        return votes.stream().map(VoteUtil::createTo).toList();
    }

    public static Boolean isOkToVote(DateUtil dateUtil) {
        return dateUtil.getCurrentHour() < hourOfStopVoting;
    }
}