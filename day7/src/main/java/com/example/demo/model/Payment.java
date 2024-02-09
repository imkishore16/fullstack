package com.example.demo.model;

import java.util.Date;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Payment {
    @Id
    private long paymentId;

    private String status;
    private Double totalAmount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    private String modeOfPayment;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer; // many to many

    @OneToOne
    @JoinColumn(name = "recharge_id", referencedColumnName = "rechargeId")
    private Recharge recharge; // One to One

}

