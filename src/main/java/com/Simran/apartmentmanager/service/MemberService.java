package com.Simran.apartmentmanager.service;

import com.Simran.apartmentmanager.dto.AddMemberRequest;
import com.Simran.apartmentmanager.dto.MemberResponse;

import java.util.List;

public interface MemberService {
    MemberResponse addMember(AddMemberRequest addMemberRequest);
    List<MemberResponse> getAllMembers();
    String deactivateMember(Long id);
}
