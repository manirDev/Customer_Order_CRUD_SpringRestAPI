package com.enoca.challenge.service;

import com.enoca.challenge.model.Customer;
import com.enoca.challenge.model.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    Order saveOrder(Order order);
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    Order updateOrder(Order order, Long id);
    void deleteOrder(Long id);

}
