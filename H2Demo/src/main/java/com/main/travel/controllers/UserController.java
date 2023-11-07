package com.main.travel.controllers;

import com.main.travel.entities.*;
import com.main.travel.services.*;
import com.travel.converter.model.ConversionResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private BookingService bookingService;
    private DestinationService destinationService;
    private TripService tripService;
    private CustomerService customerService;

    private static final Logger logger = LogManager.getLogger("myLogger");
    @Autowired
    private RestTemplate restTemplate;

    public UserController(BookingService bkgService, DestinationService destService,
                          TripService trpService, CustomerService custService) {
        bookingService = bkgService;
        destinationService = destService;
        tripService = trpService;
        customerService = custService;
    }

    //LIST ALL TRIPS
    @GetMapping("/v1/trips")
    public List<Trip> findAllTrips() {
        return tripService.findAll();
    }

    //CREATE BOOKING
    @PostMapping("/v1/trips")
    public Booking createBooking(@RequestBody Booking b) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String username = authentication.getName();

            int trip_id = b.getTrip().getId();
            Trip t = tripService.findById(trip_id);
            int dest_id = t.getDestination().getId();
            Destination d = destinationService.findById(dest_id);
            b.setId(0);
            Destination dest = destinationService.save(d);
            t.setDestination(dest);
            Trip trip = tripService.save(t);
            b.setTrip(trip);
            b.setSEK(b.calculateTotalAmount(trip));
            String converterUrl = "http://localhost:5050";
            ResponseEntity<ConversionResult> responseEntity = restTemplate.exchange(
                    converterUrl + "/convert/sek-to-pln?amount=" + b.getSEK(),
                    HttpMethod.GET,
                    null,
                    ConversionResult.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                ConversionResult conversionResult = responseEntity.getBody();
                double convertedAmount = conversionResult.getConvertedAmount();
                b.setPLN(convertedAmount);
            } else {
                logger.error("ERROR: Failed to convert " + b.getSEK() + "SEK to PLN");
            }
            Booking booking = bookingService.save(b);
            Customer c = customerService.findByUsername(username);
            c.setBooking(booking);
            Customer customer = customerService.save(c);
            logger.info("INFO: Customer ("+customer.getUsername()+") created new booking with id: " + booking.getId());
            return booking;
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
    //UPDATE (UPGRADE) BOOKING
    @PutMapping("/v1/trips/{id}")
    public Booking updateBooking(@RequestBody Booking b) {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String username = authentication.getName();

            int trip_id = b.getTrip().getId();
            Trip t = tripService.findById(trip_id);
            int dest_id = t.getDestination().getId();
            Destination d = destinationService.findById(dest_id);
            Destination dest = destinationService.save(d);
            t.setDestination(dest);
            Trip trip = tripService.save(t);
            b.setTrip(trip);
            b.setSEK(b.calculateTotalAmount(trip));
            String converterUrl = "http://localhost:5050";
            ResponseEntity<ConversionResult> responseEntity = restTemplate.exchange(
                    converterUrl + "/convert/sek-to-pln?amount=" + b.getSEK(),
                    HttpMethod.GET,
                    null,
                    ConversionResult.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                ConversionResult conversionResult = responseEntity.getBody();
                double convertedAmount = conversionResult.getConvertedAmount();
                b.setPLN(convertedAmount);
            } else {
                logger.error("ERROR: Failed to convert " + b.getSEK() + "SEK to PLN");
            }
            Customer c = customerService.findByUsername(username);
            Booking booking = bookingService.save(b);
            logger.info("INFO: Customer ("+c.getUsername()+") updated booking with id: " + booking.getId());
            return booking;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }
    //SEE PREVIUS AND ACTIVE BOOKINGS
    @GetMapping("/v1/trips/{id}")
    public Booking findByIdBooking(@PathVariable int id) {
        try {
            Booking booking = bookingService.findById(id);
            return booking;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}

