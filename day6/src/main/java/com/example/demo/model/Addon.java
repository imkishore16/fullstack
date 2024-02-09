package com.example.demo.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name ="Addons")
public class Addon {

    @Id
    private Long addonId;

    private String addonName;
    private double addonPrice;
    private String addonDetails;
    private String addonValidity;

    @OneToMany(mappedBy = "addon")
    private List<Recharge> recharges;
}