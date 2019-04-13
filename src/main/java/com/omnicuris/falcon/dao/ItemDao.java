package com.omnicuris.falcon.dao;

import com.omnicuris.falcon.model.Item;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jagadish Varma on 4/12/2019.
 */
public interface ItemDao {

    Item addItem(Item item);

    Item updateItem(Item item);

    Optional<Item> fetchItem(Long id);

    Item fetchItemByName(String name);

    void deleteItem(Long id);

    List<Item> fetchAllItems(Pageable pageable);

}
