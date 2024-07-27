package com.ft.flight.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ft.flight.entity.Booking;
import com.ft.flight.entity.BookingStatus;
import com.ft.flight.entity.Flight;
import com.ft.flight.entity.MyLinkedList;
import com.ft.flight.entity.User;
import com.ft.flight.entity.ContactForm;
import com.ft.flight.repository.BookingRepository;
import com.ft.flight.repository.FlightRepository;
import com.ft.flight.repository.UserRepository;
import com.ft.flight.service.BookingService;
import com.ft.flight.service.EmailService;
import com.ft.flight.service.FlightService;
import com.ft.flight.service.UserService;

import jakarta.servlet.http.HttpSession;

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

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EmailService emailService;

    @CrossOrigin(origins = "http://your-frontend-domain")
    @PostMapping("/submit-contact-form")
    @ResponseBody
    public ResponseEntity<ResponseDTO> submitContactForm(@RequestBody ContactForm contactForm) {
        ResponseDTO response = new ResponseDTO();
        try {
            System.out.println("contact form: " + contactForm);

            // Send the email
            emailService.sendEmail(contactForm);
            response.setMessage("Message sent successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Handle the exception and return an error message
            e.printStackTrace();
            response.setMessage("Fail sending message: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    //redirections
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

    @RequestMapping(value = "/date")
    public ModelAndView date() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("date.html");
        return modelAndView;
    }

    @RequestMapping(value = "/editing-form")
    public ModelAndView editingForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editing-form.html");
        return modelAndView;
    }

    //register
@PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterCredential registerCredential) {
        String username = registerCredential.getUsername();
        String email = registerCredential.getEmail();
        String password = registerCredential.getPassword();

        // MyLinkedList<User> userWithEmail = userRepository.findByEmail(email);
        // List<User> userWithUsername = userRepository.findByUsername(username);

        // if (!userWithEmail.isEmpty() && !userWithUsername.isEmpty()) {
        //     return ResponseEntity.badRequest().body(new ResponseDTO("The email and username have already existed!", null));
        // } else if (!userWithEmail.isEmpty()) {
        //     return ResponseEntity.badRequest().body(new ResponseDTO("The email has already existed!", null));
        // } else if (!userWithUsername.isEmpty()) {
        //     return ResponseEntity.badRequest().body(new ResponseDTO("The username has already existed!", null));
        // }

        if (!userRepository.findByEmail(email).isEmpty() && !userRepository.findByUsername(username).isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ResponseDTO("The email and username have already existed!", null));
        } else if (!userRepository.findByEmail(email).isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseDTO("The email has already existed!", null));
        } else if (!userRepository.findByUsername(username).isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseDTO("The username has already existed!", null));
        }

        if (password.length() < 8 || !containsLetterAndNumber(password)) {
            return ResponseEntity.badRequest().body(new ResponseDTO("Password must be at least 8 characters long and contain at least one letter and one number.", null));
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);

        ResponseDTO response = new ResponseDTO("Successfully registered.", "/login");
        return ResponseEntity.ok(response);
    }

    private boolean containsLetterAndNumber(String password) {
        // Check if the password contains at least one letter and one number
        return password.matches(".*[a-zA-Z].*") && password.matches(".*\\d.*");
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(
            @RequestBody LoginCredential loginCredentials,
            HttpSession session) {
        ResponseDTO response = new ResponseDTO();

        List<User> users = userRepository.findByUsername(loginCredentials.getUsername());
        if (users.isEmpty()) {
            response.setMessage("Unregistered Username!");
            return ResponseEntity.badRequest().body(response);
        }

        User user = users.get(0);
        if (!user.getPassword().equals(loginCredentials.getPassword())) {
            response.setMessage("Incorrect Password!");
            return ResponseEntity.badRequest().body(response);
        }

        session.setAttribute("userId", user.getId());
        response.setRedirect("/home"); // Specify the redirect URL

        return ResponseEntity.ok(response);
    }

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    //search by date, source and destination
    @GetMapping("/search")
    public ResponseEntity<ResponseDTO> searchFlights(
        @RequestParam String date,
        @RequestParam String source,
        @RequestParam String destination) {
        logger.info("Received search request with date: {}, source: {}, destination: {}", date, source, destination);
        
        MyLinkedList<Flight> flights = flightService.searchFlightsByDateAndSourceAndDestination(date, source, destination);
        System.out.println("peanuts:" + flights);
        List<Flight> flightList = flights.toList();
        ResponseDTO response = new ResponseDTO("redirect", "/date"); // Specify the redirect URL
        response.setFlights(flightList);
        System.out.println(flights);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/searchAllDates")
    public ResponseEntity<ResponseDTO> searchAllDates(
            @RequestParam LocalDate date,
            @RequestParam String source,
            @RequestParam String destination) {
        ResponseDTO response = new ResponseDTO();
        logger.info("Received search request with source: {}, destination: {}, and starting date: {}", source, destination, date);

        LocalDate startDate = date;

        MyLinkedList<Flight> flights = flightService.searchFlightsBySourceAndDestination(source, destination);

        // Filter flights to include only those whose dates are equal to or after the specified date
        MyLinkedList<Flight> filteredFlights = new MyLinkedList<>();

        MyLinkedList.Node<Flight> current = flights.getHead();
        while (current != null) {
            Flight flight = current.data;
            if (flight.getDate().isEqual(startDate) || flight.getDate().isAfter(startDate)) {
                filteredFlights.add(flight);
            }
            current = current.next;
        }

        // Find the cheapest flight for each day
        MyLinkedList<Flight> cheapestFlights = new MyLinkedList<>();
        current = flights.getHead();
        while (current != null) {
            Flight flight = current.data;
            date = flight.getDate();
            
            // Check if there's already a flight for this date
            boolean found = false;
            MyLinkedList.Node<Flight> cheapestCurrent = cheapestFlights.getHead();
            while (cheapestCurrent != null) {
                Flight cheapestFlight = cheapestCurrent.data;
                if (cheapestFlight.getDate().isEqual(date)) {
                    found = true;
                    if (flight.getPrice() < cheapestFlight.getPrice()) {
                        cheapestCurrent.data = flight; // Update with cheaper flight
                    }
                    break;
                }
                cheapestCurrent = cheapestCurrent.next;
            }
            
            // If no flight for this date found, add it
            if (!found) {
                cheapestFlights.add(flight);
            }
            
            current = current.next;
        }

        // Sort flights by date in ascending order
        MyLinkedList<Flight> sortedFlights = new MyLinkedList<>();
        MyLinkedList.Node<Flight> sortedCurrent = cheapestFlights.getHead();
        while (sortedCurrent != null) {
            Flight flight = sortedCurrent.data;
            if (sortedFlights.isEmpty() || flight.getDate().compareTo(sortedFlights.getHead().data.getDate()) >= 0) {
                sortedFlights.addFirst(new MyLinkedList.Node<>(flight));
            } else {
                MyLinkedList.Node<Flight> temp = sortedFlights.getHead();
                while (temp.next != null && flight.getDate().compareTo(temp.next.data.getDate()) < 0) {
                    temp = temp.next;
                }
                MyLinkedList.Node<Flight> newNode = new MyLinkedList.Node<>(flight);
                newNode.next = temp.next;
                temp.next = newNode;
            }
            sortedCurrent = sortedCurrent.next;
        }
        List<Flight> sortedFlightsList = sortedFlights.toList();
                response.setFlights(sortedFlightsList);
                return ResponseEntity.ok(response);
    }

    //date.html
    @GetMapping("/fetchFlight/{flightId}")
    public ResponseEntity<ResponseDTO> fetchFlight(@PathVariable Long flightId) {
        ResponseDTO response = new ResponseDTO();
        Flight flight = flightService.getFlightById(flightId);
        System.out.println(flight);
        response.setFlight(flight);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/booking-form/{flightId}")
    public String showBookingForm(@RequestParam Long flightId, Model model) {
        Flight flight = flightService.getFlightById(flightId);
        model.addAttribute("flight", flight);

        return "booking-form"; // Return the name of your booking form template
    }

    //book flight
    @PostMapping("/book/{flightId}")
    public ResponseEntity<String> createBooking(
        @PathVariable Long flightId,
        @RequestBody BookingForm bookingForm,
        HttpSession session
    ) {
        String passengerName=bookingForm.getPassengerName();
        String passengerEmail=bookingForm.getPassengerEmail();
        String passengerContactNo=bookingForm.getPassengerContactNo();
        String passengerPassportNo=bookingForm.getPassengerPassportNo();

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
                //update number of available seats
                flight.decreaseAvailableSeats();
                flightRepository.save(flight);

                //add to confirmedList
            }
            else if (bookingStatus == BookingStatus.WAITING){
                //add to waitingQueue

            }

            return ResponseEntity.status(HttpStatus.CREATED).body("{\"status\": \"" + bookingStatus + "\", \"message\": \"Booking created successfully\"}");
        } catch (Exception e) {
            // Handle any exceptions, such as database errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating booking");
        }
    }

    //fetch booking history by user id
    @GetMapping("/history")
    public ResponseEntity<ResponseDTO> getBookingHistory(HttpSession session) {
        ResponseDTO response = new ResponseDTO();
        Long userId = (Long) session.getAttribute("userId");
        
        if (userId == null) {
            response.setMessage("Please Login");
            return ResponseEntity.badRequest().body(response);// Handle the case where the user ID is not present in the session
        }

        List<Booking> bookingHistory = bookingService.getBookingHistoryByUserId(userId);
        response.setBookingHistory(bookingHistory);

        return ResponseEntity.ok(response);
    }


    //redirect to editing-page.html
    @GetMapping("/edit")
    public ModelAndView editBooking(@RequestParam Long bookingId) {
        ModelAndView modelAndView = new ModelAndView("editing-form");
        // Fetch and set the booking data
        Booking yourBookingData = bookingService.getBookingById(bookingId);
        modelAndView.addObject("booking", yourBookingData);
        return modelAndView;
    }

    //initialise booking data on editing page
    @GetMapping("/editinit")
    public ResponseEntity<ResponseDTO> editinitBooking(@RequestParam Long bookingId) {
        ResponseDTO response = new ResponseDTO();
        Booking booking = bookingService.getBookingById(bookingId);

        response.setBooking(booking);
        return ResponseEntity.ok(response);
    }

    //save edit booking
    @PostMapping("/save/{bookingId}")
    public ResponseEntity<ResponseDTO> saveEdit(
        @PathVariable Long bookingId,
        @RequestBody BookingForm bookingForm) {
            ResponseDTO response = new ResponseDTO();
            // Retrieve the existing Booking from the database
            Booking existingBooking = bookingService.getBookingById(bookingId);

            // Update fields with values from the bookingForm
            existingBooking.setPassengerName(bookingForm.getPassengerName());
            existingBooking.setPassengerEmail(bookingForm.getPassengerEmail());
            existingBooking.setPassengerContactNo(bookingForm.getPassengerContactNo());
            existingBooking.setPassengerPassportNo(bookingForm.getPassengerPassportNo());

            // Save the updated booking
            bookingRepository.save(existingBooking);

            response.setMessage("Booking updated successfully");
            return ResponseEntity.ok(response);
    }

    @DeleteMapping("cancel/{bookingId}")
    public ResponseEntity<ResponseDTO> cancelBooking(
        @PathVariable Long bookingId
    ){
        ResponseDTO response = new ResponseDTO();
        
        //get the flight data based on bookingId
        Flight flight = flightService.getFlightByBookingId(bookingId);
        
        //initialise confirmedList and waitingQueue
        initialize(flight);

        //get the bookingId that the user wants to cancel
        Booking existingBooking = bookingService.getBookingById(bookingId);
        
        //change the status of the existingBooking
        //if user want to cancel COMFIRMED booking
        if(existingBooking.getBookingStatus().equals(BookingStatus.CONFIRMED)){
            existingBooking.setBookingStatus(BookingStatus.CANCELED);

            //if no waiting queue, flight available seats increase by 1
            if(flight.getWaitingQueue().isEmpty()){
                flight.increaseAvailableSeats();
            }
            //if got waiting queue, add the first one in queue to confirmedList
            //set status to CONFIRMED
            else{
                Booking booking = flight.getWaitingQueue().dequeue();
                flight.addToConfirmedList(booking);
                booking.setBookingStatus(BookingStatus.CONFIRMED);
            }
        }

        //if user want to cancel WAITING booking
        else if(existingBooking.getBookingStatus().equals(BookingStatus.WAITING)){
            existingBooking.setBookingStatus(BookingStatus.CANCELED);
        }
        //update database
        bookingRepository.save(existingBooking);
        response.setMessage("Booking cancled successfully");
        return ResponseEntity.ok(response);
    }

    //initialise booking confirmedList and waitingQueue based on flightId
    public void initialize(Flight flight) {
        //get all the bookings of the same flightId
        List<Booking>bookings = bookingService.getBookingsByFlightId(flight.getFlightId());
        for (Booking booking : bookings) {
            //add CONFIRMED booking to confirmedList
            if (booking.getBookingStatus() == BookingStatus.CONFIRMED) {
                flight.addToConfirmedList(booking);
            } else if (booking.getBookingStatus() == BookingStatus.WAITING) {
                flight.addToWaitingQueue(booking);
            }
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
    private String passengerContactNo;
    private String passengerPassportNo;

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
    public String getPassengerContactNo(){
        return this.passengerContactNo;
    }
    public void setPassengerContactNo(String passengerContactNo) {
        this.passengerContactNo = passengerContactNo;
    }
    public String getPassengerPassportNo(){
        return this.passengerPassportNo;
    }
    public void setPassengerPassportNo(String passengerPassportNo) {
        this.passengerPassportNo = passengerPassportNo;
    }
}


class ResponseDTO {
    private String message;
    private String redirect;
    private List<Booking> bookingHistory;
    private List<Flight> flights;
    private Booking booking;
    private Flight flight;

    public ResponseDTO() {
        // Default constructor
    }

    public ResponseDTO(String message, String redirect) {
        this.message = message;
        this.redirect = redirect;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public List<Booking> getBookingHistory() {
        return bookingHistory;
    }

    public void setBookingHistory(List<Booking> bookingHistory) {
        this.bookingHistory = bookingHistory;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public Booking getBooking(){
        return booking;
    }

    public void setBooking (Booking booking){
        this.booking=booking;
    }

    public Flight getFlight(){
        return flight;
    }

    public void setFlight (Flight flight){
        this.flight=flight;
    }
}
