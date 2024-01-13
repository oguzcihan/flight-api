package com.cihan.flightservice.controllers;

import com.cihan.flightservice.dtos.FlightSearchRequest;
import com.cihan.flightservice.dtos.FlightSearchResponse;
import com.cihan.flightservice.services.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    private final FlightService flightService;

    public PublicController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/search")
    public ResponseEntity<FlightSearchResponse> searchFlights(@RequestBody FlightSearchRequest searchRequest) {
        FlightSearchResponse searchResponse = flightService.searchFlights(searchRequest);
        return ResponseEntity.ok().body(searchResponse);
    }

    @GetMapping
    public String helloWorld() {
        return "Hello world from public endpoint";
    }

}
