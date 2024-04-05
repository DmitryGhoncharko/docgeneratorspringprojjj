package com.example.docgeneratorspring.repository;

import com.example.docgeneratorspring.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car,Long> {
}
