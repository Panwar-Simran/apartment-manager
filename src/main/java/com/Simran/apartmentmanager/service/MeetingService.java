package com.Simran.apartmentmanager.service;

import com.Simran.apartmentmanager.dto.MeetingRequest;
import com.Simran.apartmentmanager.dto.MeetingResponse;

import java.util.List;

public interface MeetingService {

    MeetingResponse createMeeting(MeetingRequest request);

    List<MeetingResponse> getAllMeetings();

    List<MeetingResponse> getUpcomingMeetings();

    String deleteMeeting(Long id);
}
