package com.omnicuris.falcon.dao;

import com.omnicuris.falcon.model.Order;
import com.omnicuris.falcon.model.OrderItem;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jagadish Varma on 4/13/2019.
 */
public interface OrderDao {

    Order saveOrder(Order order);

    Order updateOrder(Order order);

    Optional<Order> getOrderDetails(long id);

    List<Order> getOrdersByEmail(String email, Pageable pageable);

    List<OrderItem> getOrderItems(long orderId);

    OrderItem addOrderItem(OrderItem orderItem);

    List<Order> findAllOrders(Pageable pageable);


}
