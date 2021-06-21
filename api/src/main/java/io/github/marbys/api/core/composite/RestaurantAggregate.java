package io.github.marbys.api.core.composite;

import java.util.List;

public class RestaurantAggregate {
    private final int restaurantId;
    private final String name;
    private final int rating;
    private final List<DishSummary> menu;
    private final ServiceAddresses serviceAddresses;

    public RestaurantAggregate() {
        this.restaurantId = 0;
        this.name = null;
        this.rating = 0;
        this.menu = null;
        this.serviceAddresses = null;
    }
    public RestaurantAggregate(int restaurantId, String name, int rating, List<DishSummary> menu, ServiceAddresses serviceAddresses) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.rating = rating;
        this.menu = menu;
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


    public ServiceAddresses getServiceAddresses() {
        return serviceAddresses;
    }
}
