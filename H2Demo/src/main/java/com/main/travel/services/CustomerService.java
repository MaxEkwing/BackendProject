package com.main.travel.services;

import com.main.travel.entities.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();

    Customer findById(int id);
    Customer findByUsername(String username);

    Customer save(Customer cust);

    void deleteById(int id);
}
