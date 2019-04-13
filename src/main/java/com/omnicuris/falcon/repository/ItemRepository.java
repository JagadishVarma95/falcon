package com.omnicuris.falcon.repository;

import com.omnicuris.falcon.model.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Jagadish Varma on 4/12/2019.
 */
public interface ItemRepository extends CrudRepository<Item,Long>,PagingAndSortingRepository<Item, Long> {

    @Query(value="select * from item i where i.name =:name", nativeQuery=true)
    Item getItemDetailsByName(String name);

    @Override
    List<Item> findAll();


}
