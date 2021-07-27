package io.github.marbys.api.composite;

import java.util.List;

public class RestaurantAggregate {
    private final int restaurantId;
    private final String name;
    private final int rating;
    private final List<DishSummary> menu;
    private final List<OrderSummary> orders;
    private final ServiceAddresses serviceAddresses;

    public RestaurantAggregate() {
        this.restaurantId = 0;
        this.name = null;
        this.rating = 0;
        this.menu = null;
        this.orders = null;
        this.serviceAddresses = null;
    }
    public RestaurantAggregate(int restaurantId, String name, int rating, List<DishSummary> menu, List<OrderSummary> orders, ServiceAddresses serviceAddresses) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.rating = rating;
        this.menu = menu;
        this.orders = orders;
        this.serviceAddresses = serviceAddresses;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public List<DishSummary> getMenu() {
        return menu;
    }

    public List<OrderSummary> getOrders() {
        return orders;
    }
    public ServiceAddresses getServiceAddresses() {
        return serviceAddresses;
    }
}
