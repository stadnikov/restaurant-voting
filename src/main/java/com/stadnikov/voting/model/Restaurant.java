package com.stadnikov.voting.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames={"name"}, name="unique_restaurant_name_idx")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Restaurant extends NamedEntity {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("name ASC")
    @OnDelete(action = OnDeleteAction.CASCADE) //https://stackoverflow.com/a/44988100/548473
//    @ApiModelProperty(hidden = true)
    private List<Food> food;

    public Restaurant(Integer id, String name, List<Food> food) {
        super(id, name);
        this.food = food;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Restaurant that = (Restaurant) o;

        return Objects.equals(name, that.name);
    }
}
