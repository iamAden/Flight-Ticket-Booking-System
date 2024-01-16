package com.ft.flight.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name="booking")
@AllArgsConstructor
@NoArgsConstructor

public class Booking  {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "flight", nullable = false)
    private Flight flight;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @Column(nullable = false)
    private String passengerName;

    @Column(nullable = false)
    private Long passengerPassportNo;

    @Column(nullable = false)
    private Long passengerContactNo;

    @Column(nullable = false)
    private String passengerEmail;

    public Booking(
        String passengerName,
        String passengerEmail,
        Long passengerContactNo,
        Long passengerPassportNo,
        BookingStatus bookingStatus,
        User user,
        Flight flight
    ){
        this.passengerContactNo=passengerContactNo;
        this.passengerName=passengerName;
        this.passengerPassportNo=passengerPassportNo;
        this.passengerEmail=passengerEmail;
        this.user=user;
        this.flight=flight;
        this.bookingStatus=bookingStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public Long getPassengerContactNo() {
        return passengerContactNo;
    }

    public void setPassengerContactNo(Long passengerContactNo) {
        this.passengerContactNo = passengerContactNo;
    }

    public Long getPassengerPassportNo() {
        return passengerPassportNo;
    }

    public void setPassengerPassportNo(Long passengerPassportNo) {
        this.passengerPassportNo = passengerPassportNo;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight_id(Flight flight) {
        this.flight = flight;
    }

    
}