package com.ft.flight.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ft.flight.entity.Booking;
import com.ft.flight.entity.BookingStatus;
import com.ft.flight.entity.Flight;
import com.ft.flight.entity.User;
import com.ft.flight.repository.BookingRepository;
import com.ft.flight.repository.FlightRepository;
import com.ft.flight.repository.UserRepository;
import com.ft.flight.service.FlightService;
import com.ft.flight.service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

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

    @RequestMapping(value = "/booking-form")
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
        response.put("redirect", "/login");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
        @RequestBody LoginCredential loginCredentials,
        HttpSession session
        ) {
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
        session.setAttribute("userId",user.getId());
        response.put("redirect", "/home"); // Specify the redirect URL

        return ResponseEntity.ok(response);
    }

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchFlights(
        @RequestParam String date,
        @RequestParam String source,
        @RequestParam String destination) {
            logger.info("Received search request with date: {}, source: {}, destination: {}", date, source, destination);
        Map<String, Object> response = new HashMap<>();
        List<Flight> flights = flightService.searchFlightsByDateAndSourceAndDestination(date, source, destination);
        response.put("redirect", "/date"); // Specify the redirect URL
        response.put("flights", flights);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchAllDates")
    public ResponseEntity<Map<String, Object>> searchAllDates(
        @RequestParam String source,
        @RequestParam String destination) {
            Map<String, Object> response = new HashMap<>();
            logger.info("Received search request with source: {}, destination: {}", source, destination);
        List<Flight> flights = flightService.searchFlightsBySourceAndDestination(source, destination);
        response.put("flights", flights);
        System.out.println("HELPPPPPPPP");
        return ResponseEntity.ok(response);
    }


    @RequestMapping(value = "/date")
    public ModelAndView date() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("date.html");
        return modelAndView;
    }

    //date.html
    @GetMapping("/fetchFlight/{flightId}")
    public ResponseEntity<Map<String, Object>> fetchFlight(@PathVariable Long flightId) {
        Map<String, Object> response = new HashMap<>();
        Flight flight = flightService.getFlightById(flightId);
        System.out.println(flight);
        response.put("flights", flight);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/booking-form/{flightId}")
    public String showBookingForm(@RequestParam Long flightId, Model model) {
        Flight flight = flightService.getFlightById(flightId);
        model.addAttribute("flight", flight);

        return "booking-form"; // Return the name of your booking form template
    }

    @PostMapping("/book/{flightId}")
    public ResponseEntity<String> createBooking(
        @PathVariable Long flightId,
        @RequestBody BookingForm bookingForm,
        HttpSession session
    ) {
        String passengerName=bookingForm.getPassengerName();
        String passengerEmail=bookingForm.getPassengerEmail();
        Long passengerContactNo=bookingForm.getPassengerContactNo();
        Long passengerPassportNo=bookingForm.getPassengerPassportNo();

        // Retrieve user ID from the session (assuming it is stored as an attribute named "userId")
        Long userId = (Long) session.getAttribute("userId");
        System.out.println("user id "+userId);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        //get user
        User user = userService.getUserById(userId);
        //System.out.println("user: "+user);
        //get flight
        Flight flight = flightService.getFlightById(flightId);
        //System.out.println("flight: "+flight);
        if (flight == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flight not found");
        }

        // Determine the booking status based on the number of available seats
        BookingStatus bookingStatus = (flight.getAvailableSeats() > 0) ? BookingStatus.CONFIRMED : BookingStatus.WAITING;
        System.out.println("BookingStatus: " + bookingStatus);
        // initialise a new Booking entity
        Booking newBooking = new Booking(
            passengerName, passengerEmail,
            passengerContactNo, passengerPassportNo,
            bookingStatus, user, flight
        );

        System.out.println("New Booking: " + newBooking);

        try {
            bookingRepository.save(newBooking);
            // If the booking status is confirmed, decrease the available seats for the flight
            if (bookingStatus == BookingStatus.CONFIRMED) {
                flight.decreaseAvailableSeats();
                flightRepository.save(flight);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body("{\"status\": \"" + bookingStatus + "\", \"message\": \"Booking created successfully\"}");
        } catch (Exception e) {
            // Handle any exceptions, such as database errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating booking");
        }
    }


    @GetMapping("/flight/{flightId}")
    public Flight getFlightById(@PathVariable Long flightId) {
        return flightService.getFlightById(flightId);
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
    private String date;
    private String source;
    private String destination;

    public String getDate(){
        return date;
    }

    public void setDate(String date) {
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
class BookingForm {
    private String passengerName;
    private String passengerEmail;
    private Long passengerContactNo;
    private Long passengerPassportNo;

    public String getPassengerName(){
        return this.passengerName;
    }
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }
    public String getPassengerEmail(){
        return this.passengerEmail;
    }
    public void setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
    }
    public Long getPassengerContactNo(){
        return this.passengerContactNo;
    }
    public void setPassengerContactNo(Long passengerContactNo) {
        this.passengerContactNo = passengerContactNo;
    }
    public Long getPassengerPassportNo(){
        return this.passengerPassportNo;
    }
    public void setPassengerPassportNo(Long passengerPassportNo) {
        this.passengerPassportNo = passengerPassportNo;
    }
}

