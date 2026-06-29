package com.Simran.apartmentmanager.repository;

import com.Simran.apartmentmanager.entity.MaintenanceCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaintenanceCycleRepository extends JpaRepository<MaintenanceCycle, Long> {
    // Check duplicate cycle for same month and year
    Boolean existsByMonthAndYear(Integer month, Integer year);

    // Find cycle by month and year
    Optional<MaintenanceCycle> findByMonthAndYear(
            Integer month, Integer year);
}
