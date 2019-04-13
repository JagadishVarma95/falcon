package com.omnicuris.falcon.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.omnicuris.falcon.exception.DataNotFoundException;
import com.omnicuris.falcon.exception.OutOfStockException;
import com.omnicuris.falcon.model.Order;
import com.omnicuris.falcon.model.ResponseObj;
import com.omnicuris.falcon.service.OrderService;

/**
 * Created by Jagadish Varma on 4/12/2019.
 */

@RestController
@RequestMapping("/order")
public class OrderController {

	private static Log log = LogFactory.getLog(OrderController.class);

	@Autowired
	OrderService orderService;

	@PostMapping("/add")
	public ResponseEntity<?> addOrder(@RequestBody Order order) {
		ResponseObj<Order> response = new ResponseObj<>();
		try {
			Order order1 = orderService.addOrder(order);
			log.info("Order with orderId : " + order1.getOrderId() + " successfully added for " + order1.getEmailId());
			response.setPayload(order1);
			response.setCode(200);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (OutOfStockException e) {
			log.info(e.fillInStackTrace());
			response.setError("Item with id : " + e.getMessage() + " quantity is more than available");
			response.setCode(409);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		} catch (DataNotFoundException e) {
			log.info(e.fillInStackTrace());
			response.setError("Item with id : " + e.getMessage() + " doesn't exist");
			response.setCode(404);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.info(e.fillInStackTrace());
			response.setError("Unable to process your request");
			response.setCode(400);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/details")
	public ResponseEntity<?> getOrderDetailsByEmail(@RequestParam(required = false) String emailId,
													@RequestParam(required = false) Integer page,
													@RequestParam(required = false) Integer size) {
		ResponseObj<List<Order>> response = new ResponseObj<>();
		List<Order> orders;
		try {
			if(size == null || size <= 0 || size > 20)	{
				size = 10;
			}if(page == null)	{
				page = 0;
			}
			if(emailId != null)	{
				orders = orderService.fetchOrdersByEmail(emailId,page,size);
			}else 	{
				orders = orderService.fetchAllOrders(page,size);
			}
			response.setPayload(orders);
			response.setCode(200);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.info(e.fillInStackTrace());
			response.setError("Unable to process your request");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/details/{id}")
	public ResponseEntity<?> getOrderDetails(@PathVariable("id") Long id) {
		ResponseObj<Order> response = new ResponseObj<>();
		try {
			response.setPayload(orderService.fetchOrderById(id));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (DataNotFoundException e) {
			log.info(e.fillInStackTrace());
			response.setError("Data not found");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.info(e.fillInStackTrace());
			response.setError("Unable to process your request");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}



}
