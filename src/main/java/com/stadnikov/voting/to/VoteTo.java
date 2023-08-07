package com.stadnikov.voting.to;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class VoteTo {

    @NotNull
    int user_id;

    @Column(name = "date_time", nullable = false)
    @NotNull LocalDateTime dateTime;

    @NotNull
    int restaurant_id;
}
