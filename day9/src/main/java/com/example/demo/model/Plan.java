package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity  
public class Plan {
    @Id
    // @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long planId;

    private String planType;
    private String planName;
    private String planValidity;
    private String planDetails;
    private double planPrice;

    @OneToMany(mappedBy = "plan")
    private List<Recharge> recharges;
}