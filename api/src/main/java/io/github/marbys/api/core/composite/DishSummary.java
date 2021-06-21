package io.github.marbys.api.core.composite;

public class DishSummary {
    private final int dishId;
    private final String name;
    private final String description;
    private final double price;

    public DishSummary() {
        this.dishId = 0;
        this.name = null;
        this.description = null;
        this.price = 0;
    }

    public DishSummary(int dishId, String name, String description, double price) {
        this.dishId = dishId;
        this.name = name;
        this.description = description;
        this.price = price;
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
}
