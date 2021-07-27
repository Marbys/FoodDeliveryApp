package io.github.marbys.api.core.order;

import io.github.marbys.api.composite.DishSummary;
import io.github.marbys.api.core.dish.Dish;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int restaurantId;
    private int orderId;
    private List<DishSummary> requestedDishes;
    private String customerAddress;
    private LocalDateTime orderCreatedAt;
    private String serviceAddress;

    public Order() {
        this.restaurantId = 0;
        this.orderId = 0;
        this.requestedDishes = null;
        this.customerAddress = null;
        this.orderCreatedAt = null;
        this.serviceAddress = null;
    }

    public Order(int restaurantId, int orderId, List<DishSummary> requestedDishes, String customerAddress, String serviceAddress) {
        this.restaurantId = restaurantId;
        this.orderId = orderId;
        this.requestedDishes = requestedDishes;
        this.customerAddress = customerAddress;
        this.orderCreatedAt =  LocalDateTime.now();
        this.serviceAddress = serviceAddress;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<DishSummary> getDishSummaries() {
        return requestedDishes;
    }

    public void setDishSummaries(List<DishSummary> requestedDishes) {
        this.requestedDishes = requestedDishes;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public LocalDateTime getOrderCreatedAt() {
        return orderCreatedAt;
    }

    public void setOrderCreatedAt(LocalDateTime orderCreatedAt) {
        this.orderCreatedAt = orderCreatedAt;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    @Override
    public String toString() {
        return "Order{" +
                "restaurantId=" + restaurantId +
                ", orderId=" + orderId +
                ", requestedDishes=" + requestedDishes +
                ", customerAddress='" + customerAddress + '\'' +
                ", orderCreatedAt=" + orderCreatedAt +
                ", serviceAddress='" + serviceAddress + '\'' +
                '}';
    }
}
