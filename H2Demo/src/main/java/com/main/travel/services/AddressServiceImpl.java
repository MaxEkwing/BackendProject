package com.main.travel.services;

import com.main.travel.dao.AddressRepository;
import com.main.travel.entities.Address;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService{
    private AddressRepository addressRepo;
    @Autowired
    public AddressServiceImpl(AddressRepository adrsRepo) {
        addressRepo = adrsRepo;
    }

    @Override
    public List<Address> findAll() {
        return addressRepo.findAll();
    }

    @Override
    public Address findById(int id) {
        Optional<Address> adrs = addressRepo.findById(id);
        Address address = null;
        if(adrs.isPresent()) {
            address = adrs.get();
        }
        else{
            throw new RuntimeException("Address with id: "+ id +" was not found" );
        }
        return address;
    }

    @Override
    @Transactional
    public Address save(Address adrs) {
        return addressRepo.save(adrs);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        addressRepo.deleteById(id);
    }
}
