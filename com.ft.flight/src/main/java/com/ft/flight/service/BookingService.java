package com.ft.flight.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ft.flight.entity.Booking;
import com.ft.flight.repository.BookingRepository;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;
    
    public List<Booking> getBookingHistoryByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }
}
