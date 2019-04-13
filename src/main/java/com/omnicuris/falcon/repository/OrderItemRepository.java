package com.omnicuris.falcon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.omnicuris.falcon.model.OrderItem;

/**
 * Created by Jagadish Varma on 4/13/2019.
 */
public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {

	@Query(value = "select * from order_item o where o.order_id =:orderId", nativeQuery = true)
	List<OrderItem> getItemsList(long orderId);

}
