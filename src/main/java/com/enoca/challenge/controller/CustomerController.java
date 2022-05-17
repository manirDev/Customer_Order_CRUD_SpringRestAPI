package com.enoca.challenge.controller;


import com.enoca.challenge.model.Customer;
import com.enoca.challenge.repository.CustomerRepository;
import com.enoca.challenge.response.Response;
import com.enoca.challenge.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
@CrossOrigin(origins = "*")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;
    //save customer api
    @PostMapping("/saveCustomer")
    public ResponseEntity<Response> saveEmployee(@RequestBody Customer customer){
        //log.info(String.format("Header invocationFrom = %s", invocationFrom));
        customerService.saveCustomer(customer);
        String customerName = customer.getName();
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Customer "+customerName+" saved successfully");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    //get all customers api
    @GetMapping("/customer/all")
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    //get customer  by id api
    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id){
        Customer customer = new Customer();
        customer = customerService.getCustomerById(id);
        Response response = new Response();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customer);
    }

    //update customer api
    @PutMapping("/updateCustomer/{id}")
    public ResponseEntity<Response> updateCustomer(@PathVariable Long id, @RequestBody Customer customer){
        Response response = new Response();
        customerService.updateCustomer(customer, id);
        try {
            String CustomerName = customer.getName();
            response.setStatusCode("200");
            response.setStatusMsg("Customer"+CustomerName+" successfully updated");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }catch (Exception e){
            response.setStatusCode("400");
            response.setStatusMsg("Customer not found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }
    }

    //delete customer api
    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<Response> deleteCustomer(@PathVariable Long id){
        Response response = new Response();
        try{
            customerService.deleteCustomer(id);
            response.setStatusCode("200");
            response.setStatusMsg("Customer successfully deleted");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }catch (Exception e){
            response.setStatusCode("400");
            response.setStatusMsg("Customer not found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }
    }
    //get customer by name
    @GetMapping("/customer/getByName")
    public List<Customer> getCustomerByName(@RequestBody Customer customer){
        if (null != customer && null != customer.getName()){
            return customerRepository.findByName(customer.getName());
        }else
            return List.of();
    }

    //get customers without order
    @GetMapping("/customer/withoutOrder")
    public List<Customer> getCustomerWithoutOrder(){
        return customerRepository.getCustomerWithoutOrder();
    }

}
