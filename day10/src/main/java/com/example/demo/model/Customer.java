package com.example.demo.model;



import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customer {
    @Id
    private Integer customerId;

    private String customerName;
    private String address;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user; // One to One

    @OneToMany(mappedBy = "customer")
    private List<Payment> payments; // One to Many

}
