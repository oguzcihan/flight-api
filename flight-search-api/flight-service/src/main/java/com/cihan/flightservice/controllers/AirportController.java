package com.cihan.flightservice.controllers;

import com.cihan.flightservice.dtos.AirportRequest;
import com.cihan.flightservice.dtos.AirportResponse;
import com.cihan.flightservice.services.AirportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/airports")
@Slf4j
@ControllerAdvice
public class AirportController {
    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping
    public ResponseEntity<Page<AirportResponse>> getAllAirports(Pageable pageable) {
        return ResponseEntity.ok().body(airportService.getAllAirports(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportResponse> getAirportById(@PathVariable Long id) {
        Optional<AirportResponse> airport = airportService.getAirportById(id);
        return airport.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AirportResponse> saveAirport(@RequestBody AirportRequest airportRequest) {
        AirportResponse savedAirport = airportService.saveAirport(airportRequest);
        return ResponseEntity.ok().body(savedAirport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirportResponse> updateAirport(@PathVariable Long id, @RequestBody AirportRequest airportRequest) {
        AirportResponse updatedAirport = airportService.updateAirport(id, airportRequest);
        return ResponseEntity.ok().body(updatedAirport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
        airportService.deleteAirport(id);
        log.info("airport delete id: {}", id);
        return ResponseEntity.noContent().build();
    }

}
