package io.github.marbys.api.core.composite;

public class ServiceAddresses {
    private final String composite;
    private final String dish;
    private final String order;
    private final String restaurant;

    public ServiceAddresses() {
        this.composite = null;
        this.dish = null;
        this.order = null;
        this.restaurant = null;
    }

    public ServiceAddresses(String composite, String dish, String order, String restaurant) {
        this.composite = composite;
        this.dish = dish;
        this.order = order;
        this.restaurant = restaurant;
    }

    public String getComposite() {
        return composite;
    }

    public String getDish() {
        return dish;
    }

    public String getOrder() {
        return order;
    }

    public String getRestaurant() {
        return restaurant;
    }
}
