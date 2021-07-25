package io.github.marbys.microservices.restaurant;

import io.github.marbys.microservices.restaurant.persistence.RestaurantEntity;
import io.github.marbys.microservices.restaurant.persistence.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional(propagation = NOT_SUPPORTED)
public class PersistenceTests {

    @Autowired
    private RestaurantRepository repository;

    private RestaurantEntity savedEntity;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
        RestaurantEntity entity = new RestaurantEntity(1, "name", 1);
        savedEntity = repository.save(entity);

        assertEqualsRestaurant(entity, savedEntity);
    }

    @Test
    public void create() {
        RestaurantEntity newEntity = new RestaurantEntity(2, "name", 3);
        repository.save(newEntity);

        RestaurantEntity foundEntity = repository.findById(newEntity.getId()).get();
        assertEqualsRestaurant(newEntity, foundEntity);
        assertEquals(2, repository.count());
    }

    @Test
    public void update() {
        savedEntity.setName("name2");
        repository.save(savedEntity);

        RestaurantEntity foundEntity = repository.findById(savedEntity.getId()).get();
        assertEquals("name2", foundEntity.getName());
        assertEquals(1, foundEntity.getVersion());
    }

    @Test
    public void delete() {
        repository.delete(savedEntity);
        assertEquals(0, repository.count());
    }

    @Test
    public void duplicateError() {
        RestaurantEntity entity = new RestaurantEntity(1, "name",  3);
        assertThrows(DataIntegrityViolationException.class, () -> repository.save(entity));
    }

    @Test
    public void optimisticLockingError() {
        RestaurantEntity entity = repository.findById(savedEntity.getId()).get();
        RestaurantEntity entity2 = repository.findById(savedEntity.getId()).get();

        entity.setName("name1");
        repository.save(entity);

        try {
            entity2.setName("name2");
            repository.save(entity2);
            fail("Expected exception here");
        } catch (OptimisticLockingFailureException e) {
            RestaurantEntity updatedEntity = repository.findById(savedEntity.getId()).get();

            assertEquals(1, updatedEntity.getVersion());
            assertEquals("name1", updatedEntity.getName());
        }
        
    }

    private void assertEqualsRestaurant(RestaurantEntity expectedEntity, RestaurantEntity actualEntity) {
        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getVersion(), actualEntity.getVersion());
        assertEquals(expectedEntity.getRestaurantId(), actualEntity.getRestaurantId());
        assertEquals(expectedEntity.getName(), actualEntity.getName());
        assertEquals(expectedEntity.getRating(), actualEntity.getRating());
    }

}
