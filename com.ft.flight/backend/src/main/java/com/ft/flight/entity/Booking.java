package com.ft.flight.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Passenger> passengers;

    public Booking(User user, Flight flight, BookingStatus booked) {
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }


    public String generateTicket() {
        StringBuilder ticket = new StringBuilder();
        ticket.append("Booking Information:\n");
        ticket.append("Booking ID: ").append(id).append("\n");
        ticket.append("User: ").append(user.getUsername()).append("\n");
        ticket.append("Flight: ").append(flight.getFlightName()).append("\n");
        ticket.append("Status: ").append(status).append("\n");
        // Add more details based on your requirements
        return ticket.toString();
    }
}
