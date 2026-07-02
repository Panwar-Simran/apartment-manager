package com.Simran.apartmentmanager.service;

import com.Simran.apartmentmanager.dto.MemberCreditResponse;
import com.Simran.apartmentmanager.entity.MemberCredit;
import com.Simran.apartmentmanager.entity.User;
import com.Simran.apartmentmanager.exception.BadRequestException;
import com.Simran.apartmentmanager.exception.ResourceNotFoundException;
import com.Simran.apartmentmanager.repository.MemberCreditRepository;
import com.Simran.apartmentmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Service
public class MemberCreditServiceImpl implements MemberCreditService{

    @Autowired
    MemberCreditRepository memberCreditRepository;

    @Autowired
    UserRepository userRepository;

    //get logged in member credit details
    @Override
    public MemberCreditResponse getMyCredit() {
        //get email of logged in member
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        //else find that user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        MemberCredit credit = memberCreditRepository.findByUserId(user.getId()).orElse(null);

        MemberCreditResponse response = new MemberCreditResponse();
        response.setUserId(user.getId());
        response.setMemberName(user.getName());
        response.setFlatNumber(user.getFlatNumber());

        if (credit != null) {//if credit return that credited amount
            response.setCreditBalance(credit.getCreditBalance());
            response.setUpdatedAt(credit.getUpdatedAt());
        } else { //otherwise return 0 just in case not to return error
            response.setCreditBalance(BigDecimal.ZERO);
            response.setUpdatedAt(null);
        }

        return response;
    }


    //get credit balance

    @Override
    public BigDecimal getCreditBalance(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        MemberCredit credit = memberCreditRepository.findByUserId(userId).orElse(null);

        if (credit == null) {
            return BigDecimal.ZERO;
        }

        return credit.getCreditBalance();
    }


    //add credits
    @Override
    public void addCredit(Long userId, BigDecimal amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        MemberCredit credit = memberCreditRepository.findByUserId(userId).orElse(null);
        if (credit == null) {
            credit = new MemberCredit();
            credit.setUser(user);
            credit.setCreditBalance(BigDecimal.ZERO);
        }
        credit.setCreditBalance(credit.getCreditBalance().add(amount));
        credit.setUpdatedAt(LocalDateTime.now());
        memberCreditRepository.save(credit);

    }

    //deduct credits
    @Override
    public void deductCredit(Long userId, BigDecimal amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        MemberCredit memberCredit = memberCreditRepository.findByUserId(userId).orElse(null);

        if (memberCredit == null) {
            throw new ResourceNotFoundException("Member credit not found");

        }

        if (memberCredit.getCreditBalance().compareTo(amount) < 0) {
            throw new BadRequestException("Insufficient Credit Balance");
        }
        memberCredit.setCreditBalance(memberCredit.getCreditBalance().subtract(amount));
        //why? subtract because BigDecimal is an object
        memberCredit.setUpdatedAt(LocalDateTime.now());

        memberCreditRepository.save(memberCredit);


    }


}
