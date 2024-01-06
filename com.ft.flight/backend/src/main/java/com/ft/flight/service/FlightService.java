package com.ft.flight.service;

import com.ft.flight.entity.Flight;
import com.ft.flight.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightService {

    @Autowired
    FlightRepository flightRepository;

    public Flight getFlightById(Long flightId) {
        return flightRepository.findById(flightId).orElse(null);
    }
}
