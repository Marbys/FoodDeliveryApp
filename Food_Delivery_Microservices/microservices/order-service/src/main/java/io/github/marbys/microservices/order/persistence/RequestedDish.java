package io.github.marbys.microservices.order.persistence;


import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class RequestedDish {

    private int dishId;

    private String name;
    private String description;
    private double price;

    public RequestedDish() {
    }


    public RequestedDish(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
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


}
