package com.omnicuris.falcon.service;

import com.omnicuris.falcon.dao.ItemDao;
import com.omnicuris.falcon.exception.DataNotFoundException;
import com.omnicuris.falcon.exception.DuplicateDataException;
import com.omnicuris.falcon.model.Item;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jagadish Varma on 4/12/2019.
 */

@Service
public class ItemService{

    private static Log log = LogFactory.getLog(ItemService.class);

    @Autowired
    ItemDao itemDao;

    public Item addItem(Item item) throws Exception {
        if(itemDao.fetchItemByName(item.getName()) == null) {
            return itemDao.addItem(item);
        }else   {
          throw new DuplicateDataException("");
        }
    }

    public Item updateItem(Item item) throws Exception  {
        Optional<Item> item1= itemDao.fetchItem(item.getId());
        if(!item1.isPresent())   {
            throw new DataNotFoundException("Invalid item id");
        }
        item1.get().setQuantity(item.getQuantity());
        return itemDao.updateItem(item1.get());
    }

    public void decrementCount(long id,int count)   {
        Optional<Item> item= itemDao.fetchItem(id);
        item.get().setQuantity(item.get().getQuantity() - count);
        itemDao.updateItem(item.get());
    }

    public Optional<Item> getItem(long id)    {
        return itemDao.fetchItem(id);
    }

    public void deleteItem(Long id) throws Exception {
        if(this.getItem(id).isPresent()) {
            itemDao.deleteItem(id);
        }else {
            throw new DataNotFoundException("Invalid item id");
        }
    }

    public List<Item> getAllItems(int page,int size) {
        Pageable pageable = PageRequest.of(page, size);
        return itemDao.fetchAllItems(pageable);
    }


}
