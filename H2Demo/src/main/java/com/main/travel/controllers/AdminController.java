package com.main.travel.controllers;

import com.main.travel.entities.*;
import com.main.travel.services.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminController {
    private CustomerService customerService;
    private AddressService addressService;
    private BookingService bookingService;
    private DestinationService destinationService;
    private TripService tripService;
    private static final Logger logger = LogManager.getLogger("myLogger");
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public AdminController(CustomerService custService, AddressService adrsService,
                           BookingService bkgService, DestinationService destService,
                           TripService trpService) {
        customerService = custService;
        addressService = adrsService;
        bookingService = bkgService;
        destinationService = destService;
        tripService = trpService;
    }


    //LIST CUSTOMERS
    @GetMapping("/v1/customers")
    public List<Customer> findAllCustomer() {return customerService.findAll();}


    //CREATE CUSTOMERS WITH ADDRESS
    @PostMapping("/v1/customers")
    public Customer createCustomer(@RequestBody Customer c) {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String loggedUsername = authentication.getName();

            c.setId(0);
            Address a = c.getAddress();
            a.setId(0);
            Address address = addressService.save(a);
            c.setAddress(address);
            Customer customer = customerService.save(c);

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String username = customer.getUsername();
            String hashedPassword = passwordEncoder.encode(customer.getPassword());

            String insertUserQuery = "INSERT INTO users (username, password, enabled) VALUES (?, ?, 1)";
            jdbcTemplate.update(insertUserQuery, username, hashedPassword);

            String insertAuthorityQuery = "INSERT INTO authorities (username, authority) VALUES (?, 'ROLE_USER')";
            jdbcTemplate.update(insertAuthorityQuery, username);

            logger.info("INFO: Admin (" + loggedUsername + ") created new customer with id: " + customer.getId());
            return customer;
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }

    //UPDATE CUSTOMERS WITH ADDRESS
    @PutMapping("/v1/customers/{id}")
    public Customer updateCustomer(@RequestBody Customer c) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String loggedUsername = authentication.getName();

            Address a = c.getAddress();
            Address address = addressService.save(a);
            c.setAddress(address);
            if (c.getBooking() != null){
                int booking_id = c.getBooking().getId();
                Booking b = bookingService.findById(booking_id);
                c.setBooking(b);
            }
            Customer customer = customerService.save(c);
            logger.info("INFO: Admin (" + loggedUsername + ") updated customer with id: " + customer.getId());
            return customer;
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }

    //DELETE CUSTOMERS
    @DeleteMapping("/v1/customers/{id}")
    public String deleteCustomer(@PathVariable int id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String loggedUsername = authentication.getName();

            Customer customer = customerService.findById(id);
            int address_id = customer.getAddress().getId();
            customerService.deleteById(id);
            addressService.deleteById(address_id);
            logger.info("INFO: Admin (" + loggedUsername + ") deleted customer with id: " + customer.getId());
            return "Delete Completed";
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return "Delete Failed";
    }

    //LIST BOOKINGS
    @GetMapping("/v1/destinations")
    public List<Booking> findAllBookings() {return bookingService.findAll();}

    //CREATE TRIP
    @PostMapping("/v1/destinations")
    public Trip createTrip(@RequestBody Trip t) {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String loggedUsername = authentication.getName();

            t.setId(0);
            Destination d = t.getDestination();
            d.setId(0);
            Destination dest = destinationService.save(d);
            t.setDestination(dest);
            Trip trip = tripService.save(t);
            logger.info("INFO: Admin (" + loggedUsername + ") created new trip with id: " + trip.getId());
            return trip;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }
    //UPDATE TRIP
    @PutMapping("/v1/destinations/{id}")
    public Trip updateTrip(@RequestBody Trip t) {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String loggedUsername = authentication.getName();

            Destination d = t.getDestination();
            Destination dest = destinationService.save(d);
            t.setDestination(dest);
            Trip trip = tripService.save(t);

            logger.info("INFO: Admin (" + loggedUsername + ") updated trip with id: " + trip.getId());
            return trip;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }
    //DELETE TRIP
    @DeleteMapping("/v1/destinations/{id}")
    public String deleteTrip(@PathVariable int id) {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String loggedUsername = authentication.getName();

            Trip trip = tripService.findById(id);
            int dest_id = trip.getDestination().getId();
            tripService.deleteById(id);
            destinationService.deleteById(dest_id);
            logger.info("INFO: Admin (" + loggedUsername + ") deleted trip with id: " + trip.getId());
            return "Delete Completed";
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return "Delete Failed";
    }
}
