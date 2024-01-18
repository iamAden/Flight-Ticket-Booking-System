package com.ft.flight.repository;

import com.ft.flight.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ft.flight.entity.MyLinkedList;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByDateAndSourceAndDestination(LocalDate date, String source, String destination);
    List<Flight> findBySourceAndDestination(String source, String destination);
    Optional<Flight> findById(Long id);
    Optional<Flight> findByBookingId(Long id);
}
