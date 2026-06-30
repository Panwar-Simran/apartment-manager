package com.Simran.apartmentmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "payment_records",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"cycle_id", "user_id"}))
public class PaymentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cycle_id", nullable = false)
    private MaintenanceCycle cycle;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal paidAmount;

    @Column(nullable = false)
    private BigDecimal creditUsed;

    @Column(nullable = false)
    private BigDecimal finalDue;

    private String paymentMode;
    private String transactionRef;
    private String screenshotUrl;

    @Column(nullable = false)
    private String status;

    private LocalDate paymentDate;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
