package com.stadnikov.voting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames={"user_id", "date_time"}, name="unique_userid_datetime")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Vote extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
    @NotNull
//    private int user_id;
    private User user;

    @Column(name = "date_time", nullable = false)
    @NotNull
//    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
    @NotNull
//    private int restaurant_id;
    private Restaurant restaurant;

    @Override
    public String toString() {
        return "Vote{" +
                "user=" + user +
                ", dateTime=" + dateTime +
                ", restaurant=" + restaurant +
                ", id=" + id +
                '}';
    }
}
