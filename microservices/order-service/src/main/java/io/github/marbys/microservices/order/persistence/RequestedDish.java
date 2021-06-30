package io.github.marbys.microservices.order.persistence;

import javax.persistence.*;

@Entity
public class RequestedDish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dishId;
    @Version
    private int version;

    @ManyToOne
    @JoinColumn(name = "order_idx")
    private OrderEntity orderEntity;

    public RequestedDish() {
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


}
