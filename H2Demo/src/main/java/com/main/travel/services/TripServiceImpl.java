package com.main.travel.services;

import com.main.travel.dao.TripRepository;
import com.main.travel.entities.Trip;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TripServiceImpl implements TripService {
    private TripRepository tripRepo;
    @Autowired
    public TripServiceImpl(TripRepository trpRepo) {
        tripRepo = trpRepo;
    }

    @Override
    public List<Trip> findAll() {
        return tripRepo.findAll();
    }

    @Override
    public Trip findById(int id) {
        Optional<Trip> trp = tripRepo.findById(id);
        Trip trip = null;
        if(trp.isPresent()) {
            trip = trp.get();
        }
        else{
            throw new RuntimeException("Trip with id: "+ id +" was not found" );
        }
        return trip;
    }

    @Override
    @Transactional
    public Trip save(Trip trp) {
        return tripRepo.save(trp);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        tripRepo.deleteById(id);
    }
}
