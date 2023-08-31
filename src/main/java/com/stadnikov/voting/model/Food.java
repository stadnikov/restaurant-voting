package com.stadnikov.voting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Entity
@Table(name = "food", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "food_date", "name"},
        name = "unique_restaurantid_date_name_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Food extends NamedEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
//    @NotNull
    private Restaurant restaurant;

    @Column(name = "food_date", nullable = false)
    @NotNull
    @Basic
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 10, max = 5000)
    private Integer price;

    public Food(Integer id, String name, Restaurant restaurant, LocalDate date, Integer price) {
        super(id, name);
        this.restaurant = restaurant;
        this.date = date;
        this.price = price;
    }
}
