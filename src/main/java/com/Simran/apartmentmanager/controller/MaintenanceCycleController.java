package com.Simran.apartmentmanager.controller;

import com.Simran.apartmentmanager.dto.MaintenanceCycleRequest;
import com.Simran.apartmentmanager.dto.MaintenanceCycleResponse;
import com.Simran.apartmentmanager.service.MaintenanceCycleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceCycleController {
    @Autowired
    MaintenanceCycleService maintenanceCycleService;

    //Create a cycle
    @PostMapping("/create")
    public ResponseEntity<MaintenanceCycleResponse>createCycle(@Valid @RequestBody MaintenanceCycleRequest request){
        return new ResponseEntity<>(maintenanceCycleService.createCycle(request), HttpStatus.CREATED);
    }


    //Get all cycles

    @GetMapping("/all")
    public ResponseEntity<List<MaintenanceCycleResponse>>getAllCycles(){
        return new ResponseEntity<>(maintenanceCycleService.getAllCycles(),HttpStatus.OK);
    }

}
