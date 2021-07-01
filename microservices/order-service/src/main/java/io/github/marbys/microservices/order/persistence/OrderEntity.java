package io.github.marbys.microservices.order.persistence;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Version
    private int version;

    private int restaurantId;
    private int orderId;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "orderEntity")
    private List<RequestedDish> requestedDishes = new ArrayList<>();
    private String customerAddress;
    private LocalDateTime orderCreatedAt;

    public OrderEntity() {
    }

    public OrderEntity(int restaurantId, int orderId, List<RequestedDish> requestedDishes, String customerAddress, LocalDateTime orderCreatedAt) {
        this.restaurantId = restaurantId;
        this.orderId = orderId;
        this.requestedDishes = requestedDishes;
        this.customerAddress = customerAddress;
        this.orderCreatedAt = orderCreatedAt;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<RequestedDish> getRequestedDishes() {
        return requestedDishes;
    }

    public void setRequestedDishes(List<RequestedDish> requestedDishes) {
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

    @Override
    public String toString() {
        return "OrderEntity{" +
                ", restaurantId=" + restaurantId +
                ", orderId=" + orderId +
                ", requestedDishes=" + requestedDishes +
                ", customerAddress='" + customerAddress + '\'' +
                ", orderCreatedAt=" + orderCreatedAt +
                '}';
    }
}
