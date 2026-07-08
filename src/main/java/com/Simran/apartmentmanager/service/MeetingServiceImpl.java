package com.Simran.apartmentmanager.service;

import com.Simran.apartmentmanager.dto.MeetingRequest;
import com.Simran.apartmentmanager.dto.MeetingResponse;
import com.Simran.apartmentmanager.entity.Meeting;
import com.Simran.apartmentmanager.entity.User;
import com.Simran.apartmentmanager.exception.ResourceNotFoundException;
import com.Simran.apartmentmanager.repository.MeetingRepository;
import com.Simran.apartmentmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public MeetingResponse createMeeting(MeetingRequest request){

        String email= SecurityContextHolder.getContext().getAuthentication().getName();

        User pradhana= userRepository.findByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException("User not found")
        );

        Meeting meeting=new Meeting();
        meeting.setCreatedBy(pradhana);
        meeting.setAgenda(request.getAgenda());
        meeting.setTitle(request.getTitle());
        meeting.setMeetingDate(request.getMeetingDate());
        meeting.setMeetingTime(request.getMeetingTime());
        meeting.setLocation(request.getLocation());
        meeting.setCreatedAt(LocalDateTime.now());

        Meeting savedMeeting=meetingRepository.save(meeting);

        MeetingResponse meetingResponse=new MeetingResponse();
        meetingResponse.setId(savedMeeting.getId());
        meetingResponse.setTitle(savedMeeting.getTitle());
        meetingResponse.setAgenda(savedMeeting.getAgenda());
        meetingResponse.setMeetingDate(savedMeeting.getMeetingDate());
        meetingResponse.setMeetingTime(savedMeeting.getMeetingTime());
        meetingResponse.setLocation(savedMeeting.getLocation());
        meetingResponse.setCreatedAt(savedMeeting.getCreatedAt());
        meetingResponse.setCreatedByName(savedMeeting.getCreatedBy().getName());

        return meetingResponse;

    }

    @Override
    public List<MeetingResponse> getAllMeetings(){
        List<MeetingResponse>meetingResponseList=new ArrayList<>();
        List<Meeting>meetings=meetingRepository.findAll();

        for(Meeting meeting:meetings){
            MeetingResponse meetingResponse=new MeetingResponse();
            meetingResponse.setId(meeting.getId());
            meetingResponse.setTitle(meeting.getTitle());
            meetingResponse.setAgenda(meeting.getAgenda());
            meetingResponse.setMeetingTime(meeting.getMeetingTime());
            meetingResponse.setMeetingDate(meeting.getMeetingDate());
            meetingResponse.setLocation(meeting.getLocation());
            meetingResponse.setCreatedAt(meeting.getCreatedAt());
            meetingResponse.setCreatedByName(meeting.getCreatedBy().getName());

            meetingResponseList.add(meetingResponse);
        }
        return meetingResponseList;
    }

    @Override
    public List<MeetingResponse> getUpcomingMeetings(){
        // Get meetings from today onwards
        List<Meeting> meetings = meetingRepository
                .findByMeetingDateGreaterThanEqualOrderByMeetingDateAsc(
                        LocalDate.now());
        List<MeetingResponse> responseList = new ArrayList<>();

        for (Meeting meeting : meetings) {
            MeetingResponse response = new MeetingResponse();
            response.setId(meeting.getId());
            response.setTitle(meeting.getTitle());
            response.setAgenda(meeting.getAgenda());
            response.setMeetingDate(meeting.getMeetingDate());
            response.setMeetingTime(meeting.getMeetingTime());
            response.setLocation(meeting.getLocation());
            response.setCreatedByName(meeting.getCreatedBy().getName());
            response.setCreatedAt(meeting.getCreatedAt());
            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public String deleteMeeting(Long id){
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Meeting not found with id: " + id));

        meetingRepository.delete(meeting);

        return "Meeting deleted successfully";
    }
}
