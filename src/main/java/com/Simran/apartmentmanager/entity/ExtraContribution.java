package com.Simran.apartmentmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="extra_contributions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtraContribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
