package com.main.travel.services;

import com.main.travel.entities.Trip;

import java.util.List;

public interface TripService {
    List<Trip> findAll();

    Trip findById(int id);

    Trip save(Trip trp);

    void deleteById(int id);
}
