package com.enoca.challenge.repository;

import com.enoca.challenge.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c inner join c.orders o where c.name like %:name%")
    List<Customer> findByName(@Param("name") String name);

    @Query("SELECT c FROM Customer c left join c.orders o where o.customer.id is null")
    List<Customer> getCustomerWithoutOrder();
}
