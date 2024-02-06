package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Addon;

public interface AddonRepository extends JpaRepository<Addon, Long>{
    Page<Addon> findAll(Pageable pageable);
}
