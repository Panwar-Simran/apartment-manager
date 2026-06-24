package com.Simran.apartmentmanager.service;

import com.Simran.apartmentmanager.dto.AddMemberRequest;
import com.Simran.apartmentmanager.dto.MemberResponse;
import com.Simran.apartmentmanager.entity.User;
import com.Simran.apartmentmanager.exception.BadRequestException;
import com.Simran.apartmentmanager.exception.ResourceNotFoundException;
import com.Simran.apartmentmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MemberServiceImpl implements MemberService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
    public MemberResponse addMember(AddMemberRequest addMemberRequest)
    {
        //check duplicate email
      if(userRepository.existsByEmail(addMemberRequest.getEmail())){
          throw new BadRequestException("User with this Email Id already exists!! "+addMemberRequest.getEmail());
      }

      //check duplicate flat number
        if(userRepository.existsByFlatNumber(addMemberRequest.getFlatNumber())){
            throw new BadRequestException("Flat number already exists!! "+addMemberRequest.getFlatNumber());
        }

        //Generate temp Password
        String tempPassword = "APT@" + UUID.randomUUID()//UUID.randomUUID()→ generates random unique string
                .toString().substring(0, 6).toUpperCase();

        //Create User
        User user=new User();
        user.setName(addMemberRequest.getName());
        user.setEmail(addMemberRequest.getEmail());
        user.setFlatNumber(addMemberRequest.getFlatNumber());
        user.setTempPassword(tempPassword);
        user.setPassword(passwordEncoder.encode(tempPassword));
        user.setIsPasswordChanged(false);
        user.setPhone(addMemberRequest.getPhone());
        user.setRole("Member");
        user.setIsActive(true);
        user.setCreatedAt(LocalDateTime.now());

        //save user in repo

      User savedUser=  userRepository.save(user);
      //why savedUser it returns id generated also

        //  Send welcome email
        emailService.sendWelcomeEmail(
                savedUser.getEmail(),
                savedUser.getName(),
                tempPassword
        );

        //returning member response
        MemberResponse memberResponse = new MemberResponse();
        memberResponse.setId(savedUser.getId());
        memberResponse.setName(savedUser.getName());
        memberResponse.setEmail(savedUser.getEmail());
        memberResponse.setPhone(savedUser.getPhone());
        memberResponse.setFlatNumber(savedUser.getFlatNumber());
        memberResponse.setRole(savedUser.getRole());
        memberResponse.setIsActive(savedUser.getIsActive());
        memberResponse.setIsPasswordChanged(savedUser.getIsPasswordChanged());
        memberResponse.setCreatedAt(savedUser.getCreatedAt());

        return memberResponse;

    }


    @Override
    public List<MemberResponse> getAllMembers()
    {
         List<User>users=userRepository.findAll();
         List<MemberResponse>memberResponseList=new ArrayList<>();
         for(User user:users)
         {
             MemberResponse memberResponse=new MemberResponse();
             memberResponse.setId(user.getId());
             memberResponse.setName(user.getName());
             memberResponse.setEmail(user.getEmail());
             memberResponse.setPhone(user.getPhone());
             memberResponse.setFlatNumber(user.getFlatNumber());
             memberResponse.setRole(user.getRole());
             memberResponse.setIsActive(user.getIsActive());
             memberResponse.setIsPasswordChanged(user.getIsPasswordChanged());
             memberResponse.setCreatedAt(user.getCreatedAt());
             memberResponseList.add(memberResponse);
         }
        return memberResponseList;
    }


    @Override
    public String deactivateMember(Long id)
    {
        // Step 1 - Find member by id
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Member not found with id: " + id));

        // Step 2 - Set inactive
        user.setIsActive(false);

        // Step 3 - Save
        userRepository.save(user);

        return "Member deactivated successfully";
    }
}
