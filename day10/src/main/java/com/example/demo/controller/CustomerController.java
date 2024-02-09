package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import com.example.demo.model.Addon;
import com.example.demo.model.Customer;
import com.example.demo.model.Payment;
import com.example.demo.model.Plan;
import com.example.demo.model.Recharge;
import com.example.demo.repository.AddonRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.PlanRepository;
import com.example.demo.repository.RechargeRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

// @CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final PlanRepository planRepository; 
    private final AddonRepository addonRepository;
    private final CustomerRepository customerRepository;
    private final RechargeRepository rechargeRepository;
    private final PaymentRepository paymentRepository;

    public CustomerController(PlanRepository planRepository, AddonRepository addonRepository, CustomerRepository customerRepository, RechargeRepository rechargeRepository, PaymentRepository paymentRepository) {
        this.planRepository = planRepository;
        this.addonRepository = addonRepository;
        this.customerRepository = customerRepository;
        this.rechargeRepository = rechargeRepository;
        this.paymentRepository = paymentRepository;
    }

    // retrieve all the plan details
    @GetMapping("/plan")
    public ResponseEntity<Page<Plan>> viewPlans(Pageable pageable) {
        Page<Plan> plansPage = planRepository.findAll(pageable);

        if (plansPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(plansPage, HttpStatus.OK);
        }
    }

    //retrieve all the addon plans available
    @GetMapping("/addon")
public ResponseEntity<Page<Addon>> getAllAddons(Pageable pageable) {
    try {
        Page<Addon> addonsPage = addonRepository.findAll(pageable);
        return ResponseEntity.ok(addonsPage);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
    
    
    @PostMapping("/recharge/plan/{planId}")
    public ResponseEntity<String> addRecharge(@PathVariable Long planId, @RequestBody Integer customerId) {
        System.out.println(customerId);
        try{
            if (customerId == null) {
                return ResponseEntity.badRequest().body("customerId is required");
            }
    
            Plan plan = planRepository.findById(planId).orElse(null);
            if (plan == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("plan not found");

            }
    
            // Perform recharge process
            Recharge recharge = new Recharge();
            recharge.setRechargePrice(plan.getPlanPrice());
            recharge.setStatus("false");
            recharge.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            recharge.setPlan(plan);
            recharge.setAddon(null);
    
            Payment payment = new Payment();
            payment.setStatus("false");
            payment.setTotalAmount(plan.getPlanPrice());
            payment.setPaymentDate(new Date()); // Set payment date appropriately
            payment.setModeOfPayment(null);
    
            Customer customer = customerRepository.findById(customerId).orElse(null);
            if (customer == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("cutomer not found");

            }
    
            payment.setCustomer(customer);
            recharge.setPayment(payment);

            paymentRepository.save(payment);
            rechargeRepository.save(recharge);

            return ResponseEntity.ok("Plan was recharged successfully");
            
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
            // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Plan not found");
        }
    }
    @PostMapping("/recharge/addon/{addonId}")
    public ResponseEntity<String> addAddon(@PathVariable Long addonId, @RequestBody Integer customerId) {

        try{
            Addon addon=addonRepository.findById(addonId).get();

            Recharge r=new Recharge();
            r.setRechargePrice(addon.getAddonPrice());
            r.setStatus("false");
            r.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            r.setPlan(null);
            r.setAddon(addon);
            
            Payment p = new Payment();
            p.setStatus("false");
            p.setTotalAmount(addon.getAddonPrice());
            p.setPaymentDate(null);
            p.setModeOfPayment(null);
            Customer customer=customerRepository.findById(customerId).get();
            p.setCustomer(customer);

            r.setPayment(p);

            return ResponseEntity.ok("Addon added successfully");
            
        } 
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
            // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Plan not found");
        }
    }

    // retrieve all the recharges of the user , consider userId as customerId
    @GetMapping("/{userId}")
    public ResponseEntity<List<Recharge>> ViewRecharges(@PathVariable Integer userId) {
        try {
            Customer customer=customerRepository.findById(userId).get();

            List<Recharge> arr = new ArrayList<>();
            List<Payment> payments = customer.getPayments();

            for(int i=0;i<payments.size();i++)
            {
                if(payments.get(i).getStatus().equals("false"))
                {
                    arr.add(payments.get(i).getRecharge());
                }
            }
            return ResponseEntity.ok(arr);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();


        }
    }

    //Edit recharge
    // @PutMapping("/recharge/{planId}")
    // public ResponseEntity<String> editRecharge(@PathVariable Long planId, @RequestBody Integer customerId) {


    //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");

    // }

    //Delete recharge
    @DeleteMapping("/recharge/{rechargeId}")
    public ResponseEntity<String> deleteRecharge(@PathVariable Long rechargeId, @RequestBody Integer customerId) {
        try {
            rechargeRepository.deleteById(rechargeId);
            return ResponseEntity.status(HttpStatus.OK).body("Recharge Deleted Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }


    }

    //Making payment , client sends the Payment data after he pays the money.
    @PostMapping("/makepayment")
    public ResponseEntity<String> makePayment(@RequestBody Payment paymentRequest) {
        try {
            Payment payment = paymentRepository.findById(paymentRequest.getPaymentId()).orElseThrow(() -> new RuntimeException("Payment not found"));
            payment.setStatus("true");
            payment.setPaymentDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            payment.setModeOfPayment("razorpay");
            paymentRepository.save(payment);
            return ResponseEntity.status(HttpStatus.OK).body("Payment Successfullt Made!!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
        
        
    }
        
}