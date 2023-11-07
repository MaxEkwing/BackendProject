package com.main.travel.services;

import com.main.travel.entities.Address;
import java.util.List;

public interface AddressService {
    List<Address> findAll();

    Address findById(int id);

    Address save(Address adrs);

    void deleteById(int id);
}
