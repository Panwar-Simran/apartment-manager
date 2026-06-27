package com.Simran.apartmentmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
//when we have to make combination of two columns unique we use this
@Table(name = "maintenance_cycle",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"month", "year"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceCycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer month;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private BigDecimal amountPerMember;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}


