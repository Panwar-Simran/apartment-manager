package com.Simran.apartmentmanager.service;

import com.Simran.apartmentmanager.dto.MaintenanceCycleRequest;
import com.Simran.apartmentmanager.dto.MaintenanceCycleResponse;

import java.util.List;

public interface MaintenanceCycleService {

    MaintenanceCycleResponse createCycle(MaintenanceCycleRequest request);

    List<MaintenanceCycleResponse> getAllCycles();
}
