package com.ft.flight.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Time;
import java.util.Date;
import java.util.Queue;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.List;
@Data
@Entity
@Table(name="flight")
@AllArgsConstructor
@NoArgsConstructor

public class Flight {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column
    private LocalDate date;

    @Column
    private Time departure;

    @Column
    private Time arrival;

    @Column
    private String source;

    @Column
    private String destination;

    @Column
    private String company;

    @Column
    private int availableSeats;

    @Column
    private String flightName; //eg MH2883

    @Column
    private int price;

    @Transient
    private MyLinkedList<Booking> confirmedList = new MyLinkedList<>();

    @Transient
    private MyQueue<Booking> waitingQueue = new MyQueue<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "flight")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Booking> bookings ;
    
    // Constructor or initialization method to initialize the lists
    public Flight(List<Booking> bookings) {
        if (bookings != null) {
            for (Booking booking : bookings) {
                if (booking.getBookingStatus() == BookingStatus.CONFIRMED) {
                    confirmedList.add(new MyLinkedList.Node<>(booking));
                } else if (booking.getBookingStatus() == BookingStatus.WAITING) {
                    waitingQueue.enqueue(booking);
                }
            }
        }
    }

    // Getters and Setters

    public Long getFlightId() {
        return id;
    }

    public void setFlightId(Long id) {
        this.id = id;
    }

    public Time getDeparture() {
        return departure;
    }

    public void setDeparture(Time departure) {
        this.departure = departure;
    }

    public Time getArrival() {
        return arrival;
    }

    public void setArrival(Time arrival) {
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void decreaseAvailableSeats(){
        availableSeats--;
    }

    public void addToComfirmedList(Flight flight){
        
    }

}


