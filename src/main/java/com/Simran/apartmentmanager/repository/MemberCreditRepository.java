package com.Simran.apartmentmanager.repository;

import com.Simran.apartmentmanager.entity.MemberCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Repository
public interface MemberCreditRepository extends JpaRepository<MemberCredit,Long> {
    Optional<MemberCredit> findByUserId(Long userId);
}
