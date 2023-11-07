package com.main.travel.services;

import com.main.travel.entities.Booking;

import java.util.List;

public interface BookingService {
    List<Booking> findAll();

    Booking findById(int id);

    Booking save(Booking bkg);

    void deleteById(int id);
}

