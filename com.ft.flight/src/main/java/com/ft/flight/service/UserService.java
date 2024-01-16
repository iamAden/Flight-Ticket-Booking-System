package com.ft.flight.service;

import com.ft.flight.entity.Flight;
import com.ft.flight.entity.User;
import com.ft.flight.repository.FlightRepository;
import com.ft.flight.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

}
