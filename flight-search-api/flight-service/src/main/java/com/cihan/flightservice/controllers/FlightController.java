package com.cihan.flightservice.controllers;

import com.cihan.flightservice.dtos.FlightRequest;
import com.cihan.flightservice.dtos.FlightResponse;
import com.cihan.flightservice.services.FlightService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/flights")
@Slf4j
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public ResponseEntity<Page<FlightResponse>> getAllFlights(Pageable pageable) {
        Page<FlightResponse> flights = flightService.getAllFlights(pageable);
        return ResponseEntity.ok().body(flights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponse> getFlightById(@PathVariable Long id) {
        Optional<FlightResponse> flight = flightService.getFlightById(id);
        return flight.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FlightResponse> saveFlight(@Valid @RequestBody FlightRequest flightRequest) {
        FlightResponse savedFlight = flightService.saveFlight(flightRequest);
        return ResponseEntity.ok().body(savedFlight);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponse> updateFlight(@PathVariable Long id, @RequestBody FlightRequest flightRequest) {
        FlightResponse updatedFlight = flightService.updateFlight(id, flightRequest);
        return ResponseEntity.ok().body(updatedFlight);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        log.info("flight deleted {}", id);
        return ResponseEntity.noContent().build();
    }

}
