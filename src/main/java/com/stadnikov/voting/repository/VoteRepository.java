package com.stadnikov.voting.repository;

import com.stadnikov.voting.model.Vote;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v LEFT JOIN FETCH v.restaurant WHERE CAST(v.dateTime as date) = :date")
    List<Vote> getVotesByDate(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date);

    @Cacheable("votes_today")
    default List<Vote> getTodayVotes() {
        return getVotesByDate(java.sql.Date.valueOf(LocalDate.now()));
    }

    @Query("SELECT v FROM Vote v LEFT JOIN FETCH v.restaurant " +
            "WHERE CAST(v.dateTime as date) = CURRENT_DATE AND v.user.id = :userId")
    Vote getTodayByUserId(int userId);
}
