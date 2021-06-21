package io.github.marbys.api.core.order;

import io.github.marbys.api.core.dish.Dish;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int orderId;
    private List<Dish> requestedDishes;
    private String customerAddress;
    private LocalDateTime orderCreatedAt;
    private String serviceAddress;

    public Order() {
        this.orderId = 0;
        this.requestedDishes = null;
        this.customerAddress = null;
        this.orderCreatedAt = null;
        this.serviceAddress = null;
    }

    public Order(int orderId, List<Dish> requestedDishes, String customerAddress, LocalDateTime orderCreatedAt, String serviceAddress) {
        this.orderId = orderId;
        this.requestedDishes = requestedDishes;
        this.customerAddress = customerAddress;
        this.orderCreatedAt = orderCreatedAt;
        this.serviceAddress = serviceAddress;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<Dish> getRequestedDishes() {
        return requestedDishes;
    }

    public void setRequestedDishes(List<Dish> requestedDishes) {
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
}
