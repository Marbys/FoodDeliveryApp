package io.github.marbys.api.composite;

import org.springframework.stereotype.Component;

@Component
public class DishSummary {
    private final int restaurantId;
    private final int dishId;
    private final String name;
    private final String description;
    private final double price;

    public DishSummary() {
        this.restaurantId = 0;
        this.dishId = 0;
        this.name = null;
        this.description = null;
        this.price = 0;
    }


    public DishSummary(int restaurantId, int dishId, String name, String description, double price) {
        this.restaurantId = restaurantId;
        this.dishId = dishId;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public int getDishId() {
        return dishId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "DishSummary{" +
                "restaurantId=" + restaurantId +
                ", dishId=" + dishId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
