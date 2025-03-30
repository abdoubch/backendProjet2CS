package com.example.demo.repository;

import com.example.demo.model.Problemes;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProblemesRepository extends JpaRepository<Problemes, Integer> {
}
