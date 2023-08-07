package com.stadnikov.voting.repository;

import com.stadnikov.voting.model.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface VoteRepository extends BaseRepository<Vote> {

    //get spec.(rid) rest with its today food
    @Query("select vote from Vote vote LEFT JOIN FETCH vote.restaurant where cast(vote.dateTime as date) = :date")
    List<Vote> getVotesByDate(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date);

    default List<Vote> getTodayVotes() {
        return getVotesByDate(java.sql.Date.valueOf(LocalDate.now()));
    }

    @Query("select vote from Vote vote LEFT JOIN FETCH vote.restaurant " +
            "where cast(vote.dateTime as date) = CURRENT_DATE AND vote.user.id = :userId")
    Vote getTodayByUserId(int userId);
}
