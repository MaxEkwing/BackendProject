package com.main.travel.services;

import com.main.travel.entities.Destination;

import java.util.List;

public interface DestinationService {
    List<Destination> findAll();

    Destination findById(int id);

    Destination save(Destination dest);

    void deleteById(int id);
}
