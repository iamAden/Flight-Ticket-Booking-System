package com.ft.flight.service;

import com.ft.flight.entity.Booking;
import com.ft.flight.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public String bookSeat(Long userId, Long flightId) {
        // Implement your logic to book a seat and update the status
        // Save the booking in the repository
        return "Booking successful!";
    }

    public String cancelBooking(Long bookingId) {
        // Implement your logic to cancel a booking and update the status
        // Remove the booking from the repository or update the status
        return "Booking canceled!";
    }

    public String generateTicket(Long bookingId) {
        // Retrieve the booking from the repository
        Booking booking = bookingRepository.findById(bookingId).orElse(null);

        if (booking != null) {
            return booking.generateTicket();
        } else {
            return "Booking not found.";
        }
    }

    public List<Booking> findBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }
}
