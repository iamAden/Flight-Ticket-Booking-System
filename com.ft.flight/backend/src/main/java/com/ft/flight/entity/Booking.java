package com.ft.flight.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="booking")
@AllArgsConstructor
@NoArgsConstructor
public class Booking  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @ManyToOne
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

    @Column
    @Transient
    private MyLinkedList<Booking> confirmedList = new MyLinkedList<>();

    @Column
    @Transient
    private MyQueue<Booking> waitingQueue = new MyQueue<>();

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