package com.ft.flight.controller;

import com.ft.flight.entity.Booking;
import com.ft.flight.entity.BookingStatus;
import com.ft.flight.entity.Flight;
import com.ft.flight.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public String bookSeat(@RequestParam Long userId, @RequestParam Long flightId) {
        return bookingService.bookSeat(userId, flightId);
    }

    @PostMapping("/cancel")
    public String cancelBooking(@RequestParam Long bookingId) {
        return bookingService.cancelBooking(bookingId);
    }

    @GetMapping("/ticket/{bookingId}")
    public String generateTicket(@PathVariable Long bookingId) {
        return bookingService.generateTicket(bookingId);
    }
    // ... other booking-related endpoints
}


