package io.github.marbys.microservices.order;

import io.github.marbys.microservices.order.persistence.OrderEntity;
import io.github.marbys.microservices.order.persistence.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataMongoTest
@RunWith(SpringRunner.class)
public class PersistenceTests {

    @Autowired
    private OrderRepository repository;
    private OrderEntity savedEntity;

    @BeforeEach
    public void setUp() {
        repository.deleteAll().block();
        OrderEntity entity = new OrderEntity(1, 1,  Collections.singletonList(null), "address");
        savedEntity = repository.save(entity).block();

        assertEqualsOrder(entity, savedEntity);
    }

    @Test
    public void create() {
        OrderEntity newEntity = new OrderEntity(2,2, Collections.singletonList(null), "address");
        repository.save(newEntity).block();

        OrderEntity foundEntity = repository.findById(newEntity.getId()).block();
        assertEqualsOrder(newEntity, foundEntity);
        assertEquals(2, repository.count().block());
    }

    @Test
    public void update() {
        savedEntity.setCustomerAddress("address2");
        repository.save(savedEntity).block();

        OrderEntity foundEntity = repository.findById(savedEntity.getId()).block();
        assertEquals("address2", foundEntity.getCustomerAddress());
        assertEquals(2, foundEntity.getVersion());
    }

    @Test
    public void delete() {
        repository.delete(savedEntity).block();
        assertEquals(0, repository.count().block());
    }

    @Test
    public void duplicateError() {
        OrderEntity entity = new OrderEntity(1, 1,  Collections.singletonList(null), "address");
        assertThrows(DuplicateKeyException.class, () -> repository.save(entity).block());
    }

    @Test
    public void optimisticLockingError() {
        OrderEntity entity = repository.findById(savedEntity.getId()).block();
        OrderEntity entity2 = repository.findById(savedEntity.getId()).block();

        entity.setCustomerAddress("addr1");
        repository.save(entity).block();

        try {
            entity2.setCustomerAddress("addr2");
            repository.save(entity2).block();
            fail("Expected exception here");
        } catch (OptimisticLockingFailureException e) {
            OrderEntity updatedEntity = repository.findById(savedEntity.getId()).block();

            assertEquals(2, updatedEntity.getVersion());
            assertEquals("addr1", updatedEntity.getCustomerAddress());
        }
    }

    private void assertEqualsOrder(OrderEntity expectedEntity, OrderEntity actualEntity) {
        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getVersion(), actualEntity.getVersion());
        assertEquals(expectedEntity.getOrderId(), actualEntity.getOrderId());
        assertEquals(expectedEntity.getRestaurantId(), actualEntity.getRestaurantId());
        //assertEquals(expectedEntity.getOrderCreatedAt(), actualEntity.getOrderCreatedAt());
        //assertEquals(0, expectedEntity.getOrderCreatedAt().compareTo(actualEntity.getOrderCreatedAt()));
        assertEquals(expectedEntity.getRequestedDishes(), actualEntity.getRequestedDishes());
        assertEquals(expectedEntity.getCustomerAddress(), actualEntity.getCustomerAddress());
    }

}
