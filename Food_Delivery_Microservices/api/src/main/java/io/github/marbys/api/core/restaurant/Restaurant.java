package io.github.marbys.api.core.restaurant;

public class Restaurant {
    private int restaurantId;
    private String name;
    private int rating;
    private String serviceAddress;

    public Restaurant() {
        this.restaurantId = 0;
        this.name = null;
        this.rating = 0;
        this.serviceAddress = null;
    }

    public Restaurant(int restaurantId, String name, int rating, String serviceAddress) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.rating = rating;
        this.serviceAddress = serviceAddress;
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

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }
}
