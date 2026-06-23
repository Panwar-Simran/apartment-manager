package com.Simran.apartmentmanager.controller;

import com.Simran.apartmentmanager.dto.AddMemberRequest;
import com.Simran.apartmentmanager.dto.MemberResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    //Member Management modules will have the following submodules
    //1. Add a member
    //2. View all member
    //3. Deactivate a member using member id

    @PostMapping("/add")
    public ResponseEntity<String>addMember(@Valid @RequestBody AddMemberRequest addMemberRequest)
    {
        return null;
    }

    @GetMapping("/all")
    public ResponseEntity<MemberResponse>getAllMembers() {
        return null;
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String>deactivateMember(@PathVariable Long id) {
        return null;
    }
}
