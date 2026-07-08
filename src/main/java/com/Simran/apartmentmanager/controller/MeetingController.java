package com.Simran.apartmentmanager.controller;

import com.Simran.apartmentmanager.dto.MeetingRequest;
import com.Simran.apartmentmanager.dto.MeetingResponse;
import com.Simran.apartmentmanager.service.MeetingService;
import com.Simran.apartmentmanager.service.MeetingServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    @Autowired
    MeetingService meetingService;

    // Pradhana creates meeting
    @PostMapping("/create")
    public ResponseEntity<MeetingResponse>createMeeting(
            @Valid @RequestBody MeetingRequest meetingRequest){
        return new
                ResponseEntity<>(
                        meetingService.createMeeting(meetingRequest),
                        HttpStatus.CREATED);
    }


    // Both can view all meetings
    @GetMapping("/all")
    public ResponseEntity<List<MeetingResponse>> getAllMeetings() {
        return new ResponseEntity<>(
                meetingService.getAllMeetings(),
                HttpStatus.OK);
    }

    // Both can view upcoming meetings
    @GetMapping("/upcoming")
    public ResponseEntity<List<MeetingResponse>> getUpcomingMeetings() {
        return new ResponseEntity<>(
                meetingService.getUpcomingMeetings(),
                HttpStatus.OK);
    }


    // Pradhana deletes meeting
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteMeeting(
            @PathVariable Long id) {
        return new ResponseEntity<>(
                meetingService.deleteMeeting(id),
                HttpStatus.OK);
    }


}
