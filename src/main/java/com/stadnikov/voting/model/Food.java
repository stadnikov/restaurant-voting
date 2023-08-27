package com.stadnikov.voting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Entity
@Table(name = "food", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "name", "date"}, name = "unique_restaurant_id_name_date")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food extends NamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
//    @NotNull
    private Restaurant restaurant;

    @Column(name = "date", nullable = false)
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

    @Override
    public String toString() {
        return "Food{" +
                "restaurant=" + restaurant +
                ", date=" + date +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
