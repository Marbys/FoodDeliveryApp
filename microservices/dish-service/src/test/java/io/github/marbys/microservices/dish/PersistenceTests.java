package io.github.marbys.microservices.dish;

import io.github.marbys.api.core.dish.Dish;
import io.github.marbys.microservices.dish.persistence.DishEntity;
import io.github.marbys.microservices.dish.persistence.DishRepository;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional(propagation = NOT_SUPPORTED)
public class PersistenceTests {

    @Autowired
    private DishRepository repository;

    private DishEntity savedEntity;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
        DishEntity entity = new DishEntity(1, 2, "name", "desc", 3.0);
        savedEntity = repository.save(entity);

        assertEqualsDish(entity, savedEntity);
    }

    @Test
    public void create() {
        DishEntity newEntity = new DishEntity(2,3 , "name", "desc", 4.0);
        repository.save(newEntity);

        DishEntity foundEntity = repository.findById(newEntity.getId()).get();
        assertEqualsDish(newEntity, foundEntity);
        assertEquals(2, repository.count());
    }

    @Test
    public void update() {
        savedEntity.setName("name2");
        repository.save(savedEntity);

        DishEntity foundEntity = repository.findById(savedEntity.getId()).get();
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
        DishEntity entity = new DishEntity(1, 2, "name", "desc",  3.0);
        assertThrows(DataIntegrityViolationException.class, () -> repository.save(entity));
    }

    @Test
    public void optimisticLockingError() {
        DishEntity entity = repository.findById(savedEntity.getId()).get();
        DishEntity entity2 = repository.findById(savedEntity.getId()).get();

        entity.setName("name1");
        repository.save(entity);

        try {
            entity2.setName("name2");
            repository.save(entity2);
            fail("Expected exception here");
        } catch (OptimisticLockingFailureException e) {
            DishEntity updatedEntity = repository.findById(savedEntity.getId()).get();

            assertEquals(1, updatedEntity.getVersion());
            assertEquals("name1", updatedEntity.getName());
        }

    }

    private void assertEqualsDish(DishEntity expectedEntity, DishEntity actualEntity) {
        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getVersion(), actualEntity.getVersion());
        assertEquals(expectedEntity.getRestaurantId(), actualEntity.getRestaurantId());
        assertEquals(expectedEntity.getDishId(),actualEntity.getDishId());
        assertEquals(expectedEntity.getName(), actualEntity.getName());
        assertEquals(expectedEntity.getDescription(), actualEntity.getDescription());
        assertEquals(expectedEntity.getPrice(), actualEntity.getPrice(), 0);
    }

}
