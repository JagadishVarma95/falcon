package com.omnicuris.falcon.dao.imp;

import com.omnicuris.falcon.dao.ItemDao;
import com.omnicuris.falcon.model.Item;
import com.omnicuris.falcon.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jagadish Varma on 4/12/2019.
 */

@Repository
public class ItemDaoImp implements ItemDao {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Optional<Item> fetchItem(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public Item fetchItemByName(String name) {
        return itemRepository.getItemDetailsByName(name);
    }

    @Override
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public List<Item> fetchAllItems(Pageable pageable) {
        return itemRepository.findAll(pageable).getContent();
    }
}
