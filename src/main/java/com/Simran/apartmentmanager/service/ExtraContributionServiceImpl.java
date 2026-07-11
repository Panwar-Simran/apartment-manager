package com.Simran.apartmentmanager.service;

import com.Simran.apartmentmanager.dto.ExtraContributionRequest;
import com.Simran.apartmentmanager.dto.ExtraContributionResponse;
import com.Simran.apartmentmanager.entity.ExtraContribution;
import com.Simran.apartmentmanager.entity.User;
import com.Simran.apartmentmanager.exception.BadRequestException;
import com.Simran.apartmentmanager.exception.ResourceNotFoundException;
import com.Simran.apartmentmanager.repository.ExtraContributionRepository;
import com.Simran.apartmentmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExtraContributionServiceImpl implements ExtraContributionService {

    @Autowired
    ExtraContributionRepository extraContributionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MemberCreditService memberCreditService;

    @Override
    public ExtraContributionResponse submitContribution(ExtraContributionRequest request) {
        // Step 1 - Get logged in member
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User member = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        // Step 2 - Create contribution
        ExtraContribution contribution = new ExtraContribution();
        contribution.setUser(member);
        contribution.setDescription(request.getDescription());
        contribution.setAmount(request.getAmount());
        contribution.setContributionDate(request.getContributionDate());
        contribution.setProofUrl(request.getProofUrl());
        contribution.setStatus("PENDING");
        contribution.setCreatedAt(LocalDateTime.now());

        // Step 3 - Save contribution
        ExtraContribution savedContribution =
                extraContributionRepository.save(contribution);

        // Step 4 - Build response
        ExtraContributionResponse response = new ExtraContributionResponse();

        response.setId(savedContribution.getId());
        response.setUserId(savedContribution.getUser().getId());
        response.setMemberName(savedContribution.getUser().getName());
        response.setFlatNumber(
                savedContribution.getUser().getFlatNumber());
        response.setDescription(savedContribution.getDescription());
        response.setAmount(savedContribution.getAmount());
        response.setContributionDate(
                savedContribution.getContributionDate());
        response.setProofUrl(savedContribution.getProofUrl());
        response.setStatus(savedContribution.getStatus());
        response.setApprovedByName(null);
        response.setCreatedAt(savedContribution.getCreatedAt());

        return response;
    }

    @Override
    @Transactional
    public ExtraContributionResponse approveContribution(Long id) {
        // Step 1 - Find contribution

        ExtraContribution contribution = extraContributionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Contribution not found with id: " + id));

        // Step 2 - Check if pending
        if (!contribution.getStatus().equals("PENDING")) {
            throw new BadRequestException("Contribution is not in pending state!");
        }

        // Step 3 - Get logged in Pradhana
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User pradhana = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Step 4 - Approve contribution
        contribution.setStatus("APPROVED");
        contribution.setApprovedBy(pradhana);

        // Step 5- Save Contribution
        ExtraContribution savedContribution = extraContributionRepository.save(contribution);

        // Step 6 - Add credit to member's account
        memberCreditService.addCredit(savedContribution.getUser().getId(), savedContribution.getAmount());

        // Step 7 - Build response
        ExtraContributionResponse response = new ExtraContributionResponse();
        response.setId(savedContribution.getId());
        response.setUserId(savedContribution.getUser().getId());
        response.setMemberName(savedContribution.getUser().getName());
        response.setFlatNumber(savedContribution.getUser().getFlatNumber());
        response.setDescription(savedContribution.getDescription());
        response.setAmount(savedContribution.getAmount());
        response.setContributionDate(savedContribution.getContributionDate());
        response.setProofUrl(savedContribution.getProofUrl());
        response.setStatus(savedContribution.getStatus());
        response.setApprovedByName(savedContribution.getApprovedBy().getName());
        response.setCreatedAt(savedContribution.getCreatedAt());

        return response;

    }


    @Override
    public ExtraContributionResponse rejectContribution(Long id) {
        // Step 1 - Find contribution
        ExtraContribution contribution =
                extraContributionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Contribution not found with id: " + id));

        // Step 2 - Check if pending
        if (!contribution.getStatus().equals("PENDING")) {
            throw new BadRequestException(
                    "Contribution is not in pending state!");
        }

        // Step 3 - Get logged in Pradhana
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User pradhana = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        // Step 4 - Reject contribution
        contribution.setStatus("REJECTED");
        contribution.setApprovedBy(pradhana);

        ExtraContribution savedContribution =
                extraContributionRepository.save(contribution);

        // Step 5 - Build response
        ExtraContributionResponse response = new ExtraContributionResponse();
        response.setId(savedContribution.getId());
        response.setUserId(savedContribution.getUser().getId());
        response.setMemberName(savedContribution.getUser().getName());
        response.setFlatNumber(savedContribution.getUser().getFlatNumber());
        response.setDescription(savedContribution.getDescription());
        response.setAmount(savedContribution.getAmount());
        response.setContributionDate(savedContribution.getContributionDate());
        response.setProofUrl(savedContribution.getProofUrl());
        response.setStatus(savedContribution.getStatus());
        response.setApprovedByName(savedContribution.getApprovedBy().getName());
        response.setCreatedAt(savedContribution.getCreatedAt());

        return response;
    }

    @Override
    public List<ExtraContributionResponse> getAllContributions() {
        List<ExtraContribution> contributions =
                extraContributionRepository.findAll();
        List<ExtraContributionResponse> responseList = new ArrayList<>();

        for (ExtraContribution contribution : contributions) {

            ExtraContributionResponse response = new ExtraContributionResponse();
            response.setId(contribution.getId());
            response.setUserId(contribution.getUser().getId());
            response.setMemberName(contribution.getUser().getName());
            response.setFlatNumber(contribution.getUser().getFlatNumber());
            response.setDescription(contribution.getDescription());
            response.setAmount(contribution.getAmount());
            response.setContributionDate(contribution.getContributionDate());
            response.setProofUrl(contribution.getProofUrl());
            response.setStatus(contribution.getStatus());

            // approvedBy can be null if still pending

            if (contribution.getApprovedBy() != null) {
                response.setApprovedByName(contribution.getApprovedBy().getName());
            } else {
                response.setApprovedByName(null);
            }
            response.setCreatedAt(contribution.getCreatedAt());
            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public List<ExtraContributionResponse> getMyContributions() {
        // Step 1 - Get logged in member
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User member = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Step 2 - Find all contributions by this member
        List<ExtraContribution> contributions =
                extraContributionRepository.findByUserId(member.getId());
        List<ExtraContributionResponse> responseList = new ArrayList<>();

        for (ExtraContribution contribution : contributions) {

            ExtraContributionResponse response =
                    new ExtraContributionResponse();
            response.setId(contribution.getId());
            response.setUserId(contribution.getUser().getId());
            response.setMemberName(contribution.getUser().getName());
            response.setFlatNumber(
                    contribution.getUser().getFlatNumber());
            response.setDescription(contribution.getDescription());
            response.setAmount(contribution.getAmount());
            response.setContributionDate(
                    contribution.getContributionDate());
            response.setProofUrl(contribution.getProofUrl());
            response.setStatus(contribution.getStatus());

            // approvedBy can be null if still pending
            if (contribution.getApprovedBy() != null) {
                response.setApprovedByName(
                        contribution.getApprovedBy().getName());
            } else {
                response.setApprovedByName(null);
            }

            response.setCreatedAt(contribution.getCreatedAt());
            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public List<ExtraContributionResponse> getContributionsByStatus(String status) {

        List<ExtraContribution> contributions = extraContributionRepository.findByStatus(status);
        List<ExtraContributionResponse> responseList = new ArrayList<>();

        for (ExtraContribution contribution : contributions) {
            ExtraContributionResponse response = new ExtraContributionResponse();
            response.setId(contribution.getId());
            response.setUserId(contribution.getUser().getId());
            response.setMemberName(contribution.getUser().getName());
            response.setFlatNumber(contribution.getUser().getFlatNumber());
            response.setDescription(contribution.getDescription());
            response.setAmount(contribution.getAmount());
            response.setContributionDate(contribution.getContributionDate());
            response.setProofUrl(contribution.getProofUrl());
            response.setStatus(contribution.getStatus());

            if (contribution.getApprovedBy() != null) {
                response.setApprovedByName(
                        contribution.getApprovedBy().getName());
            } else {
                response.setApprovedByName(null);
            }

            response.setCreatedAt(contribution.getCreatedAt());
            responseList.add(response);
        }

        return responseList;
    }
}
