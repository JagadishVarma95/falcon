package com.omnicuris.falcon.service;

import com.omnicuris.falcon.dao.ItemDao;
import com.omnicuris.falcon.dao.OrderDao;
import com.omnicuris.falcon.exception.DataNotFoundException;
import com.omnicuris.falcon.exception.OutOfStockException;
import com.omnicuris.falcon.model.Item;
import com.omnicuris.falcon.model.Order;
import com.omnicuris.falcon.model.OrderItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Jagadish Varma on 4/13/2019.
 */

@Service
public class OrderService {

    private static Log log = LogFactory.getLog(OrderService.class);

    @Autowired
    OrderDao orderDao;

    @Autowired
    ItemDao itemDao;

    @Autowired
    ItemService itemService;

    public Order addOrder(Order order) throws Exception {
        for(OrderItem orderItem : order.getOrderItems())    {
            Optional<Item> item =itemDao.fetchItem(orderItem.getItemId());
            if(!item.isPresent())    {
                throw new DataNotFoundException(String.valueOf(orderItem.getItemId()));
            }
            if(item.get().getQuantity() < orderItem.getQuantity())  {
                throw new OutOfStockException(String.valueOf(orderItem.getItemId()));
            }
            orderItem.setName(item.get().getName());
        }
        order.setCreatedOn(System.currentTimeMillis()/1000);
        order = orderDao.saveOrder(order);
        List<OrderItem> items = new ArrayList<>();
        for(OrderItem orderItem : order.getOrderItems())    {
            orderItem.setOrderId(order.getOrderId());
            items.add(addOrderItem(orderItem));
            itemService.decrementCount(orderItem.getItemId(),orderItem.getQuantity());
        }
        order.setOrderItems(items);
        return order;
    }

    private OrderItem addOrderItem(OrderItem orderItem)  {
        return orderDao.addOrderItem(orderItem);
    }


    public Order fetchOrderById(long id) throws Exception   {
        Optional<Order> order = orderDao.getOrderDetails(id);
        if(!order.isPresent())   {
            throw new DataNotFoundException("Invalid id");
        }
        log.info(orderDao.getOrderItems(id).get(0).getName());
        order.get().setOrderItems(orderDao.getOrderItems(id));
        return order.get();
    }

    public List<Order> fetchAllOrders(int page,int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Order> orders = orderDao.findAllOrders(pageable);
        for(Order o : orders)   {
            o.setOrderItems(orderDao.getOrderItems(o.getOrderId()));
        }
        return orders;
    }

    public List<Order> fetchOrdersByEmail(String email,int page,int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Order> orders = orderDao.getOrdersByEmail(email,pageable);
        for(Order o : orders)   {
            o.setOrderItems(orderDao.getOrderItems(o.getOrderId()));
        }
        return orders;
    }


}
