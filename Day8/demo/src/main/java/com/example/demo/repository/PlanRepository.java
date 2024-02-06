package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Plan;

public interface PlanRepository extends JpaRepository<Plan, Long>{
    Page<Plan> findAll(Pageable pageable);

}
