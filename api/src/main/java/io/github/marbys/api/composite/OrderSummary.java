package io.github.marbys.api.composite;

import io.github.marbys.api.core.dish.Dish;

import java.time.LocalDateTime;
import java.util.List;

public class OrderSummary {
    private final int orderId;
    private final List<Dish> requestedDishes;
    private final String customerAddress;
    private final LocalDateTime orderCreatedAt;

    public OrderSummary() {
        this.orderId = 0;
        this.requestedDishes = null;
        this.customerAddress = null;
        this.orderCreatedAt = null;
    }

    public OrderSummary(int orderId, List<Dish> requestedDishes, String customerAddress, LocalDateTime orderCreatedAt) {
        this.orderId = orderId;
        this.requestedDishes = requestedDishes;
        this.customerAddress = customerAddress;
        this.orderCreatedAt = orderCreatedAt;
    }

    public int getOrderId() {
        return orderId;
    }

    public List<Dish> getRequestedDishes() {
        return requestedDishes;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public LocalDateTime getOrderCreatedAt() {
        return orderCreatedAt;
    }
}
