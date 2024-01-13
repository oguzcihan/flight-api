package com.cihan.flightservice.services;

import com.cihan.flightservice.dtos.AirportRequest;
import com.cihan.flightservice.dtos.AirportResponse;
import com.cihan.flightservice.exceptions.GenException;
import com.cihan.flightservice.model.Airport;
import com.cihan.flightservice.repository.AirportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AirportService {
    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public Page<AirportResponse> getAllAirports(Pageable pageable) {
        return airportRepository.findAll(pageable)
                .map(airport -> new AirportResponse(airport.getId(), airport.getCity()));
    }

    public Optional<AirportResponse> getAirportById(Long id) {
        log.info("airport getAll id: {}", id);
        return airportRepository.findById(id)
                .map(airport -> new AirportResponse(airport.getId(), airport.getCity()));
    }

    public AirportResponse saveAirport(AirportRequest airportRequest) {
        if (airportRepository.existsByCityIgnoreCase(airportRequest.getCity())) {
            log.error("City already exists: {}", airportRequest.getCity());
            throw new GenException("City already exists");
        }
        Airport airport = new Airport();
        airport.setCity(airportRequest.getCity());

        Airport savedAirport = airportRepository.save(airport);
        log.info("airport saved: {}", savedAirport.getId());
        return new AirportResponse(savedAirport.getId(), savedAirport.getCity());
    }

    public AirportResponse updateAirport(Long id, AirportRequest airportRequest) {
        Optional<Airport> existingAirportOptional = airportRepository.findById(id);

        if (existingAirportOptional.isPresent()) {
            Airport existingAirport = existingAirportOptional.get();
            existingAirport.setCity(airportRequest.getCity());

            Airport updatedAirport = airportRepository.save(existingAirport);
            log.info("airport updated: {}", updatedAirport.getId());
            return new AirportResponse(updatedAirport.getId(), updatedAirport.getCity());
        } else {
            throw new GenException("airport not found");
        }
    }

    public void deleteAirport(Long id) {
        airportRepository.deleteById(id);
    }

}
