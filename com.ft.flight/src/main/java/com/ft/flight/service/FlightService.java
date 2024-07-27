package com.ft.flight.service;

import com.ft.flight.entity.Flight;
import com.ft.flight.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import com.ft.flight.entity.MyLinkedList;

@Service
public class FlightService {
    

    @Autowired
    FlightRepository flightRepository;

    public Flight getFlightById(Long flightId) {
        return flightRepository.findById(flightId).orElse(null);
    }

    // public MyLinkedList<Flight> searchFlightsBySourceAndDestination(String source, String destination) {
    //     MyLinkedList<Flight> flights = (MyLinkedList<Flight>) flightRepository.findBySourceAndDestination(source,
    //             destination);

    //     return flights;
    // }
    
    public MyLinkedList<Flight> searchFlightsBySourceAndDestination(String source, String destination) {
        List<Flight> flightsList = flightRepository.findBySourceAndDestination(source, destination);
        System.out.println("list:"+flightsList);
        MyLinkedList<Flight> flights = new MyLinkedList<>();
        for (Flight flight : flightsList) {
            flights.add(flight);
        }
        System.out.println("poop:"+flights);
        return flights;
    }

    // public MyLinkedList<Flight> searchFlightsByDateAndSourceAndDestination(String date, String source,
    //         String destination) {
    //     // Convert the String date to java.util.Date using SimpleDateFormat
    //     LocalDate parsedDate = parseStringToDate(date);
    //     System.out.println(parsedDate);
    //     // Call the repository method with the converted date
    //     MyLinkedList<Flight> flights = (MyLinkedList<Flight>) flightRepository
    //             .findByDateAndSourceAndDestination(parsedDate, source, destination);
    //     System.out.println("Found flights: " + flights);

    //     return flights;
    // }
    
    public MyLinkedList<Flight> searchFlightsByDateAndSourceAndDestination(String date, String source,
            String destination) {
        // Convert the String date to java.util.Date using SimpleDateFormat
        LocalDate parsedDate = parseStringToDate(date);
        // Call the repository method with the converted date
        List<Flight> flightsList = flightRepository.findByDateAndSourceAndDestination(parsedDate, source, destination);
        System.out.println("meow:" + flightsList);
        MyLinkedList<Flight> flights = new MyLinkedList<>();
        for (Flight flight : flightsList) {
            flights.add(flight);
        }
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

    public Flight getFlightByBookingId(Long bookingId) {
        return flightRepository.findByBookings_Id(bookingId).orElse(null);
    }
}
