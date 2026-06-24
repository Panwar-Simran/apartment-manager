package com.Simran.apartmentmanager.controller;

import com.Simran.apartmentmanager.dto.AddMemberRequest;
import com.Simran.apartmentmanager.dto.MemberResponse;
import com.Simran.apartmentmanager.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {


    //Member Management modules will have the following submodules
    //1. Add a member
    //2. View all member
    //3. Deactivate a member using member id

    @Autowired
    MemberService memberService;

    @PostMapping("/add")
    public ResponseEntity<MemberResponse>addMember(@Valid @RequestBody AddMemberRequest addMemberRequest)
    {

        return new ResponseEntity<>(memberService.addMember(addMemberRequest), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MemberResponse>>getAllMembers() {

        return new ResponseEntity<>(memberService.getAllMembers(),HttpStatus.OK);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String>deactivateMember(@PathVariable Long id) {
        return new ResponseEntity<>(memberService.deactivateMember(id),HttpStatus.OK);
    }
}
