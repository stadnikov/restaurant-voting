package com.stadnikov.voting.to;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Value
public class VoteTo {

    @NotNull
    int user_id;

    @Column(name = "date_time", nullable = false)
    @NotNull LocalDateTime dateTime;

    @NotNull
    int restaurant_id;

    @Override
    // if objects created dateTime differ less than 1 minute - they are the same
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VoteTo voteTo = (VoteTo) o;

        if (user_id != voteTo.user_id) return false;
        if (restaurant_id != voteTo.restaurant_id) return false;
        return ChronoUnit.MINUTES.between(dateTime, voteTo.dateTime) <= 1;
    }

    @Override
    public int hashCode() {
        int result = user_id;
        result = 31 * result + dateTime.hashCode();
        result = 31 * result + restaurant_id;
        return result;
    }
}
