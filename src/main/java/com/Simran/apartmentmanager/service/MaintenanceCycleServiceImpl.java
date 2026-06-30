package com.Simran.apartmentmanager.service;

import com.Simran.apartmentmanager.dto.MaintenanceCycleRequest;
import com.Simran.apartmentmanager.dto.MaintenanceCycleResponse;
import com.Simran.apartmentmanager.repository.MaintenanceCycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintenanceCycleServiceImpl {

@Autowired
    MaintenanceCycleRepository maintenanceCycleRepository;

    MaintenanceCycleResponse createCycle(MaintenanceCycleRequest request){

    }

    List<MaintenanceCycleResponse> getAllCycles(){

    }
}
