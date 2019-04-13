package com.omnicuris.falcon.repository;

import java.util.List;

import com.omnicuris.falcon.model.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.omnicuris.falcon.model.Order;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Jagadish Varma on 4/13/2019.
 */
public interface OrderRepository extends CrudRepository<Order, Long>,PagingAndSortingRepository<Order, Long> {

	@Query(value = "select * from order_details o where o.email_id =:email", nativeQuery = true)
	List<Order> getOrderDetailsByMail(String email);

	List<Order> findAllByEmailId(String email, Pageable pageable);

}