package com.Simran.apartmentmanager.service;

import com.Simran.apartmentmanager.dto.ExtraContributionRequest;
import com.Simran.apartmentmanager.dto.ExtraContributionResponse;

import java.util.List;

public interface ExtraContributionService {
    // Member submits extra contribution request
    ExtraContributionResponse submitContribution(ExtraContributionRequest request);

    // Pradhana approves the contribution
    ExtraContributionResponse approveContribution(Long id);

    // Pradhana rejects the contribution
    ExtraContributionResponse rejectContribution(Long id);

    // Pradhana views ALL contributions from ALL members
    List<ExtraContributionResponse> getAllContributions();

    // Member views ONLY their OWN contributions
    List<ExtraContributionResponse> getMyContributions();


    // Get all contributions by status (PENDING/APPROVED/REJECTED)
    List<ExtraContributionResponse> getContributionsByStatus(String status);
}
