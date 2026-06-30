package com.Simran.apartmentmanager.repository;

import com.Simran.apartmentmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByFlatNumber(String flatNumber);
    List<User> findAllByIsActiveTrue();
}
