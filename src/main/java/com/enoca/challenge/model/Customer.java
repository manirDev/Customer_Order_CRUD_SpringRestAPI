package com.enoca.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "customers")
public class Customer {
    @Column(name = "customer_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToMany(cascade = CascadeType.ALL )
    @JoinColumn(name = "customer_id")
    private List<Order> orders;
}
