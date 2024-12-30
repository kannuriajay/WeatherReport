package com.example.weatherapp.controller;

import com.example.weatherapp.dto.UserRequest;
import com.example.weatherapp.model.WeatherRequest;
import com.example.weatherapp.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;



import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @PostMapping("/fetch")
    @Operation(
            summary = "Fetch weather data for a postal code and user",
            description = "Fetches weather data based on postal code and username",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Weather data fetched successfully")
            }
    )
    public ResponseEntity<WeatherRequest> fetchWeather(@RequestBody WeatherRequest request) {
        return ResponseEntity.ok(weatherService.fetchWeatherData(request.getPostalCode(), request.getUsername()));
    }

    @GetMapping("/history/username")
    @Operation(
            summary = "Get weather request history by username",
            description = "Fetch weather request history based on the username provided in the request body",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Weather history fetched successfully")
            }
    )
    public ResponseEntity<List<WeatherRequest>> getHistoryByUser(@RequestBody UserRequest request) {
        String username = request.getUsername();
        return ResponseEntity.ok(weatherService.getHistoryByUser(username));
    }

    @GetMapping("/history/postalCode")
    @Operation(
            summary = "Get weather request history by postal code",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Weather history fetched successfully")
            }
    )
    public ResponseEntity<List<WeatherRequest>> getHistoryByPostalCode(@RequestBody UserRequest request) {
        String postalCode = request.getPostalCode();
        return ResponseEntity.ok(weatherService.getHistoryByPostalCode(postalCode));
    }


}
