package com.Simran.apartmentmanager.controller;

import com.Simran.apartmentmanager.dto.MemberCreditResponse;
import com.Simran.apartmentmanager.service.MemberCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/credits")
public class MemberCreditController {
    @Autowired
    private MemberCreditService memberCreditService;

    // Member views own credit balance
    @GetMapping("/my")
    public ResponseEntity<MemberCreditResponse> getMyCredit() {
        return new ResponseEntity<>(memberCreditService.getMyCredit(), HttpStatus.OK);
    }
}
