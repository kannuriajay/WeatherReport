package com.example.weatherapp.repository;

import com.example.weatherapp.model.WeatherRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherRequestRepository extends JpaRepository<WeatherRequest, Long> {
    List<WeatherRequest> findByUsername(String username);
    List<WeatherRequest> findByPostalCode(String postalCode);
}
