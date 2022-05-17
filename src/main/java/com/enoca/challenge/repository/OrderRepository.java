package com.enoca.challenge.repository;

import com.enoca.challenge.model.Customer;
import com.enoca.challenge.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.createDate > :#{#givenDate}")
    List<Order> findByCreateDate(@Param("givenDate") LocalDate givenDate);

}
