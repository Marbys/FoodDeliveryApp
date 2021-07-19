package io.github.marbys.microservices.restaurant.services;

import io.github.marbys.api.core.restaurant.Restaurant;
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

    private final RestaurantServiceImpl restaurantService;

    @Autowired
    public MessageProcessor(RestaurantServiceImpl restaurantService) {
        this.restaurantService = restaurantService;
    }

    @StreamListener(target = Sink.INPUT)
    public void process(Event<Integer, Restaurant> event) {
        switch (event.getEventType()) {
            case CREATE -> {
                LOG.info("Create restaurant with ID: {}", event.getData().getRestaurantId());
                restaurantService.createRestaurant(event.getData());
            }
            case DELETE -> {
                LOG.info("Delete restaurant with ID: {}", event.getData().getRestaurantId());
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
