package io.github.marbys.microservices.order.persistence;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "orders")
public class OrderEntity {

    @Transient
    public static final String SEQUENCE_NAME = "orders_sequence";

    @Id
    private String id;

    @Version
    private int version;
    
    private int restaurantId;

    @Indexed(unique = true)
    private int orderId;

    private List<RequestedDish> requestedDishes = new ArrayList<>();
    private String customerAddress;
    private LocalDateTime orderCreatedAt;

    public OrderEntity() {
    }

    @JsonCreator
    public OrderEntity(@JsonProperty("restaurantId") int restaurantId,
                       @JsonProperty("orderId") int orderId,
                       @JsonProperty("requestedDishes") List<RequestedDish> requestedDishes,
                       @JsonProperty("customerAddress") String customerAddress) {

        this.restaurantId = restaurantId;
        this.orderId = orderId;
        this.requestedDishes = requestedDishes;
        this.customerAddress = customerAddress;
        this.orderCreatedAt = LocalDateTime.now();
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVersion(int version) {
        this.version = version;
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
