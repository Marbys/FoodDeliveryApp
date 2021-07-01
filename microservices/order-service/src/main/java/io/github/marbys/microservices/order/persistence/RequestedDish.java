package io.github.marbys.microservices.order.persistence;

import javax.persistence.*;

@Entity
public class RequestedDish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Version
    private int version;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_idx", nullable = false)
    private OrderEntity orderEntity;
    private String name;
    private String description;
    private double price;

    public RequestedDish() {
    }

    public RequestedDish(OrderEntity orderEntity, String name, String description, double price) {
        this.orderEntity = orderEntity;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public RequestedDish(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    @Override
    public String toString() {
        return "RequestedDish{" +
                ", orderEntity=" + orderEntity +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
