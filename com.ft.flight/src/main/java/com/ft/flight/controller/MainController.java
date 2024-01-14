package com.ft.flight.controller;

import com.ft.flight.entity.*;

import com.ft.flight.repository.BookingRepository;
import com.ft.flight.repository.FlightRepository;
import com.ft.flight.repository.UserRepository;
import com.ft.flight.service.FlightService;
import com.ft.flight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
@CrossOrigin
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightService flightService;

    @Autowired
    private UserService userService;

    private User user;


    @RequestMapping(value = "/login")
    public ModelAndView start() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
        return modelAndView;
    }

    @RequestMapping(value = "/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }

    @RequestMapping(value = "/about")
    public ModelAndView about() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("about.html");
        return modelAndView;
    }

    @RequestMapping(value = "/contact")
    public ModelAndView contact() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("contact.html");
        return modelAndView;
    }

    @RequestMapping(value = "/booking")
    public ModelAndView booking() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("booking.html");
        return modelAndView;
    }

    @RequestMapping(value = "/bookingform")
    public ModelAndView bookingform() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("booking-form.html");
        return modelAndView;
    }

    @RequestMapping(value = "/edit")
    public ModelAndView edit() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editing-form.html");
        return modelAndView;
    }

    @RequestMapping(value = "/date")
    public ModelAndView date() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("date.html");
        return modelAndView;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> Register(@RequestBody RegisterCredential registerCredential) {
        Map<String, String> response = new HashMap<>();

        List<User> user_email = userRepository.findByEmail(registerCredential.getEmail());
        List<User> user_username = userRepository.findByUsername(registerCredential.getUsername());

        User user = new User();

        if (!user_email.isEmpty() && !user_username.isEmpty()) {
            response.put("message", "The email and username have already existed!");
            return ResponseEntity.badRequest().body(response);
        }
        else if (!user_email.isEmpty()) {
            response.put("message", "The email has already existed!");
            return ResponseEntity.badRequest().body(response);
        }
        else if (!user_username.isEmpty()) {
            response.put("message", "The username has already existed!");
            return ResponseEntity.badRequest().body(response);
        }

        user.setUsername(registerCredential.getUsername());
        user.setEmail(registerCredential.getEmail());
        user.setPassword(registerCredential.getPassword());
        userRepository.save(user);
        //response.put("redirect", "/home");
        response.put("message","Successfully registered.");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginCredential loginCredentials) {
        Map<String, String> response = new HashMap<>();

        List<User> users = userRepository.findByUsername(loginCredentials.getUsername());

        if (users.isEmpty()) {
            response.put("message", "Unregistered Username!");
            return ResponseEntity.badRequest().body(response);
        }

        User user = users.get(0);
        if (!user.getPassword().equals(loginCredentials.getPassword())) {
            response.put("message", "Incorrect Password!");
            return ResponseEntity.badRequest().body(response);
        }

        response.put("redirect", "/home"); // Specify the redirect URL

        return ResponseEntity.ok(response);
    }

    @GetMapping("/bookings/{id}")
    public Booking getBookingById(@PathVariable String id) {
        return bookingRepository.findById(Long.valueOf(id)).orElse(null);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Flight>> findFlight(@RequestBody FlightCredentials flightCredentials) {
        List<Flight> flights = flightRepository.findByDateAndSourceAndDestination(flightCredentials.getDate(), flightCredentials.getSource(), flightCredentials.getDestination());
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/flight/{flightId}")
    public Flight getFlightById(@PathVariable Long flightId) {
        return flightService.getFlightById(flightId);

    }

    @PostMapping("/flight/{flightId}/book/")
    public ResponseEntity<String> book(
            @PathVariable Long flightId,
            @RequestBody BookingCredentials bookingCredentials
    ) {
        // Assuming you have a method to retrieve the flight by ID
        Flight flight = flightService.getFlightById(flightId);

        Booking booking = new Booking();
        booking.setFlight(flight);
        booking.setPassengerName(bookingCredentials.getPassengerName());
        booking.setPassengerPassportNo(bookingCredentials.getPassengerPassportNo());
        booking.setPassengerContactNo(bookingCredentials.getPassengerContactNo());

        if (flight.getAvailableSeats() > 0) {
            booking.setBookingStatus(BookingStatus.BOOKED);
            flight.setAvailableSeats(flight.getAvailableSeats() - 1);
            bookingRepository.save(booking);
            return ResponseEntity.ok("{\"message\": \"Successfully booked!\"}");
        } else {
            booking.setBookingStatus(BookingStatus.WAITING);
            bookingRepository.save(booking);
            return ResponseEntity.ok("{\"message\": \"Flight is fully booked! You are in waiting list.\"}");
        }
    }


    @PutMapping("/bookings/{id}")
    public Booking editBooking(@PathVariable String id, @RequestBody Booking booking) {
        Booking existingBooking = bookingRepository.findById(Long.valueOf(id)).orElse(null);

        if (existingBooking != null) {
            existingBooking.setPassengerName(booking.getPassengerName());
            existingBooking.setPassengerPassportNo(booking.getPassengerPassportNo());
            existingBooking.setPassengerContactNo(booking.getPassengerContactNo());

            // Save the updated booking
            return bookingRepository.save(existingBooking);
        } else {
            // Handle case where the booking with the given ID is not found
            return null;
        }
    }

    @DeleteMapping("/bookings/{id}")
    public void deleteBooking(@PathVariable String id) {
        bookingRepository.deleteById(Long.valueOf(id));
    }

}
class RegisterCredential {
    private String email;
    private String username;
    private String password;
    public RegisterCredential(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
class LoginCredential {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class FlightCredentials {
    private Date date;
    private String source;
    private String destination;

    public Date getDate(){
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
class BookingCredentials{
    private String passengerName;
    private Long passengerContactNo;
    private Long passengerPassportNo;

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
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
}

