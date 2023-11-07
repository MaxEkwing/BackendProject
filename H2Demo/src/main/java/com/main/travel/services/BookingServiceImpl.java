package com.main.travel.services;

import com.main.travel.dao.BookingRepository;
import com.main.travel.entities.Booking;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService{
    private BookingRepository  bookingRepo;
    @Autowired
    public BookingServiceImpl(BookingRepository bkgRepo ) {
        bookingRepo  = bkgRepo ;
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepo.findAll();
    }

    @Override
    public Booking findById(int id) {
        Optional<Booking> bkg = bookingRepo.findById(id);
        Booking booking = null;
        if(bkg.isPresent()) {
            booking = bkg.get();
        }
        else{
            throw new RuntimeException("Booking with id: "+ id +" was not found" );
        }
        return booking;
    }

    @Override
    @Transactional
    public Booking save(Booking bkg) {
        return bookingRepo.save(bkg);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        bookingRepo.deleteById(id);
    }
}
