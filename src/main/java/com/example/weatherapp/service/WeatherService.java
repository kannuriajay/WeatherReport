package com.example.weatherapp.service;

import com.example.weatherapp.model.WeatherRequest;
import com.example.weatherapp.repository.WeatherRequestRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRequestRepository repository;
    private final RestTemplate restTemplate;

    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String API_KEY = "API_KEY";

    public WeatherRequest fetchWeatherData(String postalCode, String user) {
        String url = String.format("%s?zip=%s,us&appid=%s&units=metric", API_URL, postalCode, API_KEY);
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            JsonNode body = response.getBody();
            String condition = body.get("weather").get(0).get("description").asText();
            double temp = body.get("main").get("temp").asDouble();
            double humidity = body.get("main").get("humidity").asDouble();

            WeatherRequest request = new WeatherRequest(null, user, postalCode, condition, temp, humidity, LocalDateTime.now());
            return repository.save(request);
        } else {
            throw new RuntimeException("Failed to fetch weather data.");
        }
    }

    public List<WeatherRequest> getHistoryByUser(String username) {
        return repository.findByUsername(username);
    }

    public List<WeatherRequest> getHistoryByPostalCode(String postalCode) {
        return repository.findByPostalCode(postalCode);
    }
}

