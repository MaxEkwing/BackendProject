package com.main.travel.services;

import com.main.travel.dao.CustomerRepository;
import com.main.travel.entities.Customer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepo;
    @Autowired
    public CustomerServiceImpl(CustomerRepository custRepo) {
        customerRepo = custRepo;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepo.findAll();
    }

    @Override
    public Customer findById(int id) {
        Optional<Customer> cust = customerRepo.findById(id);
        Customer customer = null;
        if(cust.isPresent()) {
            customer = cust.get();
        }
        else{
            throw new RuntimeException("Customer with id: "+ id +" was not found" );
        }
        return customer;
    }

    @Override
    public Customer findByUsername(String username) {
        Optional<Customer> cust = customerRepo.findByUsername(username);
        Customer customer = null;
        if (cust.isPresent()) {
            customer = cust.get();
        } else {
            throw new RuntimeException("Customer with username: " + username + " was not found");
        }
        return customer;
    }

    @Override
    @Transactional
    public Customer save(Customer cust) {
        return customerRepo.save(cust);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        customerRepo.deleteById(id);
    }

}
