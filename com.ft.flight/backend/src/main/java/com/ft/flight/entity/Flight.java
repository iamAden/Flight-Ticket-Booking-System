package com.ft.flight.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Entity
public class Flight {
    // ... existing fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime departure;

    @Column(nullable = false)
    private LocalDateTime arrival;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private String airline;

    @Column(nullable = false)
    private int availableSeats;

    @Column(nullable = false)
    private String flightName; //eg MH2883

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private Queue<Booking> bookingQueue = new LinkedList<>();

    public Flight() {
    }

    public Flight(LocalDateTime departure, LocalDateTime arrival, String source, String destination, String airline, int availableSeats, String flightName) {
        this.departure = departure;
        this.arrival = arrival;
        this.source = source;
        this.destination = destination;
        this.airline = airline;
        this.availableSeats = availableSeats;
        this.flightName = flightName;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    public void setArrival(LocalDateTime arrival) {
        this.arrival = arrival;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int seat) {
        this.availableSeats = seat;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    // Add a booking to the flight, update status based on availability
    public void bookSeat(User user) {
        if (hasAvailableSeats()) {
            Booking booking = new Booking(user, this, BookingStatus.BOOKED);
            bookingQueue.add(booking);
            availableSeats--;
        } else {
            Booking booking = new Booking(user, this, BookingStatus.WAITING);
            bookingQueue.add(booking);
        }
    }

    // Methods

    // Cancel a booking and fill a seat from the waiting queue
    public void cancelBooking() {
        Booking canceledBooking = bookingQueue.poll();
        if (canceledBooking != null) {
            canceledBooking.setStatus(BookingStatus.BOOKED);
        }
    }

    // Check if there are available seats
    public boolean hasAvailableSeats() {
        return availableSeats >0;
    }


}
