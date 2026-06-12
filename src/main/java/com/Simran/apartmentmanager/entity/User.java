package com.Simran.apartmentmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String tempPassword;

    @Column(nullable = false)
    private Boolean isPasswordChanged = false;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false, unique = true)
    private String flatNumber;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private Boolean isActive = true;

    private LocalDateTime createdAt;

}
