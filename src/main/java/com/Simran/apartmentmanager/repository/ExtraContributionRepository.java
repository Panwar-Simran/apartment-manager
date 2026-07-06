package com.Simran.apartmentmanager.repository;

import com.Simran.apartmentmanager.entity.ExtraContribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtraContributionRepository extends JpaRepository<ExtraContribution,Long> {

    // Find all contributions by a specific member
    List<ExtraContribution>findByUserId(Long userId);

    // Find all contributions by status
    List<ExtraContribution> findByStatus(String status);
}
