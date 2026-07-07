package com.Simran.apartmentmanager.repository;

import com.Simran.apartmentmanager.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting,Long> {
    // Find upcoming meetings - date greater than or equal to today
    List<Meeting> findByMeetingDateGreaterThanEqualOrderByMeetingDateAsc(java.time.LocalDate date);
}
