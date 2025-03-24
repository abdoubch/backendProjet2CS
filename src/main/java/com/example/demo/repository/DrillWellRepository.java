package com.example.demo.repository;

import com.example.demo.model.DrillWell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrillWellRepository extends JpaRepository<DrillWell, Integer> {
    // You can add custom queries if needed
}
