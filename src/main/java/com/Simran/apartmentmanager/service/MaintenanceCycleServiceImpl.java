package com.Simran.apartmentmanager.service;

import com.Simran.apartmentmanager.dto.MaintenanceCycleRequest;
import com.Simran.apartmentmanager.dto.MaintenanceCycleResponse;
import com.Simran.apartmentmanager.entity.MaintenanceCycle;
import com.Simran.apartmentmanager.entity.PaymentRecord;
import com.Simran.apartmentmanager.entity.User;
import com.Simran.apartmentmanager.exception.BadRequestException;
import com.Simran.apartmentmanager.repository.MaintenanceCycleRepository;
import com.Simran.apartmentmanager.repository.PaymentRecordRepository;
import com.Simran.apartmentmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MaintenanceCycleServiceImpl implements MaintenanceCycleService {

@Autowired
    MaintenanceCycleRepository maintenanceCycleRepository;

@Autowired
    UserRepository userRepository;

@Autowired
    PaymentRecordRepository paymentRecordRepository;

    //create cycle
    @Override
    @Transactional
  public  MaintenanceCycleResponse createCycle(MaintenanceCycleRequest request){
       //1. Check Duplicate Entry
        if(maintenanceCycleRepository.existsByMonthAndYear(request.getMonth(), request.getYear()))
       {
           throw new BadRequestException("Cycle exists for month "+request.getMonth()+
                   " and Year "+request.getYear());
       }

       //2. Create cycle
        MaintenanceCycle maintenanceCycle=new MaintenanceCycle();
        maintenanceCycle.setYear(request.getYear());
        maintenanceCycle.setMonth(request.getMonth());
        maintenanceCycle.setAmountPerMember(request.getAmountPerMember());
        maintenanceCycle.setDueDate(request.getDueDate());
        maintenanceCycle.setCreatedAt(LocalDateTime.now());

        //3. Save Cycle
        MaintenanceCycle savedCycle = maintenanceCycleRepository.save(maintenanceCycle);

        // Step 4 - Fetch all active users (Pradhana + Members both pay)
        List<User> allActiveUsers = userRepository.findAllByIsActiveTrue();

        // Step 5 - Create payment record for each user using simple for loop
        List<PaymentRecord> records = new ArrayList<>();

        for (User user : allActiveUsers) {
            PaymentRecord record = new PaymentRecord();
            record.setCycle(savedCycle);
            record.setUser(user);
            record.setPaidAmount(BigDecimal.ZERO);
            record.setCreditUsed(BigDecimal.ZERO);
            record.setFinalDue(savedCycle.getAmountPerMember());
            record.setStatus("PENDING");
            record.setCreatedAt(LocalDateTime.now());

            records.add(record);
        }

        // Step 6 - Save all payment records
        paymentRecordRepository.saveAll(records);

        // Step 7 - Build response object manually
        MaintenanceCycleResponse response = new MaintenanceCycleResponse();
        response.setId(savedCycle.getId());
        response.setMonth(savedCycle.getMonth());
        response.setYear(savedCycle.getYear());
        response.setAmountPerMember(savedCycle.getAmountPerMember());
        response.setDueDate(savedCycle.getDueDate());
        response.setCreatedAt(savedCycle.getCreatedAt());

        return response;
    }

    //get all cycles
    @Override
    public List<MaintenanceCycleResponse> getAllCycles(){
       List<MaintenanceCycle>cycles=maintenanceCycleRepository.findAll();
       List<MaintenanceCycleResponse>maintenanceCycleResponses=new ArrayList<>();

       for(MaintenanceCycle maintenanceCycle:cycles)
       {
           MaintenanceCycleResponse responses=new MaintenanceCycleResponse();
           responses.setId(maintenanceCycle.getId());
           responses.setYear(maintenanceCycle.getYear());
           responses.setMonth(maintenanceCycle.getMonth());
           responses.setAmountPerMember(maintenanceCycle.getAmountPerMember());
           responses.setDueDate(maintenanceCycle.getDueDate());
           responses.setCreatedAt(maintenanceCycle.getCreatedAt());

           maintenanceCycleResponses.add(responses);
       }
       return maintenanceCycleResponses;
    }
}
