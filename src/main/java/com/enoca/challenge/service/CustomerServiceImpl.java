package com.enoca.challenge.service;

import com.enoca.challenge.model.Customer;
import com.enoca.challenge.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return  customerRepository.findById(id).get();
    }

    @Override
    public Customer updateCustomer(Customer customer, Long id) {
        //we need to check whether customer exists or not
        Customer existingCustomer = customerRepository.findById(id).get();
        if (null != existingCustomer){
            existingCustomer.setOrders(customer.getOrders());
            existingCustomer.setName(customer.getName());
            existingCustomer.setAge(customer.getAge());
        }

        //saving to the DB
        customerRepository.save(existingCustomer);
        return  existingCustomer;
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer existingCustomer = customerRepository.findById(id).get();
        if (null != existingCustomer){
            customerRepository.deleteById(id);
        }
    }

    //@Override
//    public Customer getCustomerByName(String name) {
//        //return customerRepository.findByName(name);
//    }
}
