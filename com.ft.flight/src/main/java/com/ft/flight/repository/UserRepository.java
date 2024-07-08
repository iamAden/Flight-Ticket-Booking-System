package com.ft.flight.repository;

import com.ft.flight.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
// import com.ft.flight.entity.MyLinkedList;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByEmail(String email);

    List<User> findByUsername(String username);

    Optional<User> findById(Long userId);
}

