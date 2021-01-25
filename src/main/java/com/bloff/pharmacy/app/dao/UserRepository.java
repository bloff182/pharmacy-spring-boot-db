package com.bloff.pharmacy.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bloff.pharmacy.app.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   User findByUsername(String username);
   
   List<User> findByUsernameContainsIgnoreCase(String username);
   
   Optional<User> findByEmail(String email);
   
}
