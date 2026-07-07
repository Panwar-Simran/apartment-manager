package com.Simran.apartmentmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingResponse {

    private Long id;
    private String title;
    private String agenda;
    private LocalDate meetingDate;
    private LocalTime meetingTime;
    private String location;
    private String createdByName;
    private LocalDateTime createdAt;

}
