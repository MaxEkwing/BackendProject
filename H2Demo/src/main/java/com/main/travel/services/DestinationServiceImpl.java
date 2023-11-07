package com.main.travel.services;

import com.main.travel.dao.DestinationRepository;
import com.main.travel.entities.Destination;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DestinationServiceImpl implements DestinationService{
    private DestinationRepository destinationRepo;
    @Autowired
    public DestinationServiceImpl(DestinationRepository destRepo) {
        destinationRepo = destRepo;
    }

    @Override
    public List<Destination> findAll() {
        return destinationRepo.findAll();
    }

    @Override
    public Destination findById(int id) {
        Optional<Destination> dest = destinationRepo.findById(id);
        Destination destination = null;
        if(dest.isPresent()) {
            destination = dest.get();
        }
        else{
            throw new RuntimeException("Destination with id: "+ id +" was not found" );
        }
        return destination;
    }

    @Override
    @Transactional
    public Destination save(Destination dest) {
        return destinationRepo.save(dest);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        destinationRepo.deleteById(id);
    }
}
