package com.Simran.apartmentmanager.controller;

import com.Simran.apartmentmanager.dto.ExtraContributionRequest;
import com.Simran.apartmentmanager.dto.ExtraContributionResponse;
import com.Simran.apartmentmanager.service.ExtraContributionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contributions")
public class ExtraContributionController {

    @Autowired
    private ExtraContributionService extraContributionService;

    // Member submits contribution
    @PostMapping("/submit")
    public ResponseEntity<ExtraContributionResponse> submitContribution(@Valid @RequestBody ExtraContributionRequest request) {
        return new ResponseEntity<>(
                extraContributionService.submitContribution(request), HttpStatus.CREATED);
    }

    // Pradhana approves contribution
    @PutMapping("/{id}/approve")
    public ResponseEntity<ExtraContributionResponse> approveContribution(
            @PathVariable Long id) {
        return new ResponseEntity<>(
                extraContributionService.approveContribution(id), HttpStatus.OK);
    }

    // Pradhana rejects contribution
    @PutMapping("/{id}/reject")
    public ResponseEntity<ExtraContributionResponse> rejectContribution(
            @PathVariable Long id) {
        return new ResponseEntity<>(
                extraContributionService.rejectContribution(id), HttpStatus.OK);
    }

    // Pradhana views all contributions
    @GetMapping("/all")
    public ResponseEntity<List<ExtraContributionResponse>>
    getAllContributions() {return new ResponseEntity<>(
            extraContributionService.getAllContributions(), HttpStatus.OK);
    }

    // Member views own contributions
    @GetMapping("/my")
    public ResponseEntity<List<ExtraContributionResponse>>
    getMyContributions() {
        return new ResponseEntity<>(
                extraContributionService.getMyContributions(), HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ExtraContributionResponse>>
    getContributionsByStatus(@PathVariable String status) {
        return new ResponseEntity<>(extraContributionService.getContributionsByStatus(status),
                HttpStatus.OK);
    }
}
