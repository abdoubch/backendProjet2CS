package com.example.demo.repository;

import java.util.List;
import com.example.demo.model.Planning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanningRepository extends JpaRepository<Planning, Integer> {
    List<Planning> findByDrillingWellId(int drillWellId);
}
