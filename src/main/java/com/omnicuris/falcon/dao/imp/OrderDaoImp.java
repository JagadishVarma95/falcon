package com.omnicuris.falcon.dao.imp;

import com.omnicuris.falcon.dao.OrderDao;
import com.omnicuris.falcon.model.Order;
import com.omnicuris.falcon.model.OrderItem;
import com.omnicuris.falcon.repository.OrderItemRepository;
import com.omnicuris.falcon.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jagadish Varma on 4/13/2019.
 */

@Repository
public class OrderDaoImp implements OrderDao{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> getOrderDetails(long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getOrdersByEmail(String email, Pageable pageable) {
        return orderRepository.findAllByEmailId(email,pageable);
    }

    @Override
    public List<OrderItem> getOrderItems(long orderId) {
        return orderItemRepository.getItemsList(orderId);
    }

    @Override
    public OrderItem addOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<Order> findAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).getContent();
    }
}
