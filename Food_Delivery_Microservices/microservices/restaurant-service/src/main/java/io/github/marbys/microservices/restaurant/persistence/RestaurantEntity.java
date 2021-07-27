package io.github.marbys.microservices.restaurant.persistence;

import org.springframework.stereotype.Indexed;

import javax.persistence.*;

@Entity
@Table(indexes = {@Index(name = "restaurant_unique_idx", columnList = "restaurantId", unique = true)})
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Version
    private int version;

    private int restaurantId;
    private String name;
    private int rating;

    public RestaurantEntity() {
    }

    public RestaurantEntity(int restaurantId, String name, int rating) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
