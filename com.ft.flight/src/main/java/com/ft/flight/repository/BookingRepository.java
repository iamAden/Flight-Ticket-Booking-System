package com.ft.flight.repository;

import com.ft.flight.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findById(Long id);
    List<Booking> findByUserId(Long userId);
    List<Booking> findByFlightId(Long flightId);
}

