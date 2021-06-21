package io.github.marbys.api.core.dish;

public class Dish {
    private int dishId;
    private String name;
    private String description;
    private double price;
    private String serviceAddress;

    public Dish() {
        this.dishId = 0;
        this.name = null;
        this.description = null;
        this.price = 0;
        this.serviceAddress = null;
    }

    public Dish(int dishId, String name, String description, double price, String serviceAddress) {
        this.dishId = dishId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.serviceAddress = serviceAddress;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }
}
