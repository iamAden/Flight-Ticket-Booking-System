package com.ft.flight.service;

import com.ft.flight.entity.Flight;
import com.ft.flight.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ft.flight.entity.MyLinkedList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.text.ParseException;

@Service
public class FlightService {
    

    @Autowired
    FlightRepository flightRepository;

    public Flight getFlightById(Long flightId) {
        return flightRepository.findById(flightId).orElse(null);
    }

    public List<Flight> searchFlightsBySourceAndDestination(String source, String destination){
        List<Flight> flights = flightRepository.findBySourceAndDestination(source, destination);
        
        return flights;
    }

    public List<Flight> searchFlightsByDateAndSourceAndDestination(String date, String source, String destination) {
        // Convert the String date to java.util.Date using SimpleDateFormat
        LocalDate parsedDate = parseStringToDate(date);
        System.out.println(parsedDate);
        // Call the repository method with the converted date
        List<Flight> flights = flightRepository.findByDateAndSourceAndDestination(parsedDate, source, destination);
        System.out.println("Found flights: " + flights);

        return flights;
    }

    private LocalDate parseStringToDate(String dateString) {
    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    } catch (DateTimeParseException e) {
        e.printStackTrace();
        return null;
    }
}
    
}
