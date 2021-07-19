package io.github.marbys.microservices.order.services;

import io.github.marbys.api.core.order.Order;
import io.github.marbys.api.core.order.OrderService;
import io.github.marbys.api.event.Event;
import io.github.marbys.util.exceptions.EventProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
public class MessageProcessor {
    private final static Logger LOG = LoggerFactory.getLogger(MessageProcessor.class);

    private final OrderService orderService;

    @Autowired
    public MessageProcessor(OrderService orderService) {
        this.orderService = orderService;
    }

    @StreamListener(target = Sink.INPUT)
    public void process(Event<Integer, Order> event) {
        switch (event.getEventType()) {
            case CREATE -> {
                LOG.info("Create order with ID: {}", event.getData().getOrderId());
                orderService.placeOrder(event.getData());
            }
            case DELETE -> {
                LOG.info("Delete order with ID: {}", event.getData().getOrderId());
            }
            default -> {
                String errorMessage = "Incorrect event type: " +
                        event.getEventType() + ", expected a CREATE or DELETE event";
                LOG.warn(errorMessage);
                throw new EventProcessingException(errorMessage);
            }

        }

    }
}
