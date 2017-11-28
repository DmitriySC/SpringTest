package ru.chirkovds.springtest.service.impl;

import com.google.common.collect.Lists;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chirkovds.springtest.entity.Item;
import ru.chirkovds.springtest.repositiry.ItemRepository;
import ru.chirkovds.springtest.service.ItemService;
import java.util.List;

@Repository
@Transactional
@Service("jpaItemService")
public class ItemServiceImpl implements ItemService{
    private Log log = LogFactory.getLog(ItemService.class);
    private ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public List<Item> findAll() {
        log.info("Finding all Items!!");
        List<Item> itemList = Lists.newArrayList(itemRepository.findAll());
        Hibernate.initialize(itemList);
        return itemList;
    }

    @Transactional(readOnly = true)
    public List<Item> findByClientId(Long id) {
        log.info("Finding Items by Client ID: " + id);
        List<Item> itemList = Lists.newArrayList(itemRepository.findByClientId(id));
        Hibernate.initialize(itemList);
        return itemList;
    }

    @Transactional(readOnly = true)
    public Item findOne(Long id) {
        log.info("Finding Items by ID: " + id);
        return itemRepository.findOne(id);
    }

    public Item saveItem(Item item) {
        Item savedItem = itemRepository.save(item);
        if (item.getId() == null) {
            log.info("New Item created with ID: " + savedItem.getId());
        } else {
            log.info("Item with ID: " + item.getId() + " was updated success!");
        }
        return savedItem;
    }

    public void deleteItem(Long id) {
        itemRepository.delete(id);
    }
    @Autowired
    public void setItemRepository (ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }
}
