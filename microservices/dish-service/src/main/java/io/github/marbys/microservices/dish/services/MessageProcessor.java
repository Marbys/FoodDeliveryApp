package io.github.marbys.microservices.dish.services;

import io.github.marbys.api.core.dish.Dish;
import io.github.marbys.api.core.dish.DishService;
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

    private final DishService dishService;

    @Autowired
    public MessageProcessor(DishService dishService) {
        this.dishService = dishService;
    }

    @StreamListener(target = Sink.INPUT)
    public void process(Event<Integer, Dish> event) {
        switch (event.getEventType()) {
            case CREATE -> {
                LOG.info("Create dish with ID: {}", event.getData().getDishId());
                dishService.createDish(event.getData());
            }
            case DELETE -> {
                LOG.info("Delete dish with ID: {}", event.getData().getDishId());
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
