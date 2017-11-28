package ru.chirkovds.springtest.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chirkovds.springtest.entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByClientId (Long id);
}
