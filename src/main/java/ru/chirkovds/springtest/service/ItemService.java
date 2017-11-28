package ru.chirkovds.springtest.service;

import ru.chirkovds.springtest.entity.Item;
import java.util.List;

public interface ItemService {
    List<Item> findAll();
    List<Item> findByClientId (Long id);
    Item findOne(Long Id);
    Item saveItem (Item item);
    void deleteItem (Long id);
}
