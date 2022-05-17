package com.enoca.challenge.controller;

import com.enoca.challenge.model.Customer;
import com.enoca.challenge.model.Order;
import com.enoca.challenge.repository.OrderRepository;
import com.enoca.challenge.response.Response;
import com.enoca.challenge.service.CustomerService;
import com.enoca.challenge.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
@CrossOrigin(origins = "*")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;

    //save order to a customer api
    @PostMapping("/customer/{customer_id}/saveOrder")
    public ResponseEntity<Response> saveOrder(@RequestBody Order order, @PathVariable long customer_id){
        Customer customer = customerService.getCustomerById(customer_id);
        customer.getOrders().add(order);
        orderService.saveOrder(order);
        customerService.saveCustomer(customer);
        LocalDate orderCreateDate = order.getCreateDate();
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg(" Order Successfully created at: " + orderCreateDate);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    //get all orders api
    @GetMapping("/orders")
    public List<Order> getAllCompanies(){
        return orderService.getAllOrders();
    }

    //get order  by id api
    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id){
        Order order = new Order();
        order = orderService.getOrderById(id);
        //Response response = new Response();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(order);
    }
    //get orders from customer id api
    @GetMapping("/customer/{customer_id}/orders")
    public ResponseEntity<List<Order>> getOrders(@PathVariable long customer_id) {

        return new ResponseEntity<>( customerService
                .getCustomerById(customer_id)
                .getOrders() , HttpStatus.OK);
    }

    //update Order api
    @PutMapping("/updateOrder/{id}")
    public ResponseEntity<Response> updateOrder(@PathVariable Long id, @RequestBody Order order){
        Response response = new Response();
        orderService.updateOrder(order, id);
        try {
            response.setStatusCode("200");
            response.setStatusMsg("Order successfully updated");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }catch (Exception e){
            response.setStatusCode("400");
            response.setStatusMsg("Order not found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }
    }

    //delete order api
    @DeleteMapping("/deleteOrder/{id}")
    public ResponseEntity<Response> deleteOrder(@PathVariable Long id){
        Response response = new Response();
        try{
            orderService.deleteOrder(id);
            response.setStatusCode("200");
            response.setStatusMsg("Order successfully deleted");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }catch (Exception e){
            response.setStatusCode("400");
            response.setStatusMsg("Order not found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }
    }

    //get after create date
    @GetMapping("/order/getAfterCreateDate")
    public List<Order> getOrderByCreateDate(@RequestBody Order order){

        if (null != order && null != order.getCreateDate()){
            return orderRepository.findByCreateDate(order.getCreateDate());
        }else
            return List.of();
    }
}
