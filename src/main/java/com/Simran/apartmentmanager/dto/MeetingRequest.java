package com.Simran.apartmentmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class MeetingRequest {
    @NotBlank(message = "Title is required")
    private String title;

    private String agenda;

    @NotNull(message = "Meeting date is required")
    private LocalDate meetingDate;

    @NotNull(message = "Meeting time is required")
    private LocalTime meetingTime;

    @NotBlank(message = "Location is required")
    private String location;
}
