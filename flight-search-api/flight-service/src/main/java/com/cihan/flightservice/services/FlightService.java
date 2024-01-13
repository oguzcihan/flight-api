package com.cihan.flightservice.services;

import com.cihan.flightservice.dtos.*;
import com.cihan.flightservice.exceptions.GenException;
import com.cihan.flightservice.exceptions.ResourceNotFoundException;
import com.cihan.flightservice.model.Airport;
import com.cihan.flightservice.model.Flight;
import com.cihan.flightservice.repository.FlightRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final AirportService airportService;

    public FlightService(FlightRepository flightRepository, AirportService airportService) {
        this.flightRepository = flightRepository;
        this.airportService = airportService;
    }

    public Page<FlightResponse> getAllFlights(Pageable pageable) {
        return flightRepository.findAll(pageable)
                .map(this::mapToFlightResponse);
    }

    public Optional<FlightResponse> getFlightById(Long id) {
        return Optional.ofNullable(flightRepository.findById(id)
                .map(this::mapToFlightResponse).orElseThrow(() -> new ResourceNotFoundException("flight not found")));
    }

    public FlightResponse saveFlight(FlightRequest flightRequest) {

        Flight flight = mapToFlight(flightRequest);
        Long departureAirportId = flight.getDepartureAirport().getId();
        Long arrivalAirportId = flight.getArrivalAirport().getId();
        LocalDateTime departureDateTime = flight.getDepartureDateTime();
        LocalDateTime returnDateTime = flight.getReturnDateTime();
        LocalDate today = LocalDate.now();

        if (Objects.equals(departureAirportId, arrivalAirportId) || returnDateTime.isBefore(departureDateTime)) {
            String errorMessage = (Objects.equals(departureAirportId, arrivalAirportId))
                    ? "departure and arrival airports cannot be the same"
                    : "return date time cannot be before departure date time";

            throw new GenException(errorMessage);
        }

        if (departureDateTime.toLocalDate().isBefore(today) || returnDateTime.toLocalDate().isBefore(today)) {
            throw new GenException("departure and return dates cannot be before today");
        }


        Flight savedFlight = flightRepository.save(flight);
        return mapToFlightResponse(savedFlight);
    }

    public FlightResponse updateFlight(Long id, FlightRequest flightRequest) {
        Optional<Flight> existingFlightOptional = flightRepository.findById(id);

        if (existingFlightOptional.isPresent()) {
            Flight existingFlight = existingFlightOptional.get();
            mapToFlightFromRequest(flightRequest, existingFlight);


            Flight updatedFlight = flightRepository.save(existingFlight);
            return mapToFlightResponse(updatedFlight);
        } else {
            throw new ResourceNotFoundException("Flight not found");
        }
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

    public FlightSearchResponse searchFlights(FlightSearchRequest searchRequest) {
        FlightSearchResponse searchResponse = new FlightSearchResponse();

        // Tek yönlü uçuşları ara
        List<Flight> oneWayFlights = flightRepository.searchOneWayFlights(
                searchRequest.getDepartureAirportId(),
                searchRequest.getArrivalAirportId(),
                searchRequest.getDepartureDate() //kalkış tarihi
        );
        // Tek yönlü uçuşları FlightResponse listesine dönüştür
        List<FlightResponse> oneWayFlightResponses = oneWayFlights.stream()
                .map(this::mapToFlightResponse)
                .toList();
        searchResponse.setOneWayFlights(oneWayFlightResponses);

        // Çift yönlü uçuşları ara
        if (searchRequest.getReturnDate() != null) {
            List<Flight> roundTripFlights = flightRepository.searchRoundTripFlights(
                    searchRequest.getArrivalAirportId(),
                    searchRequest.getDepartureAirportId(),
                    searchRequest.getReturnDate() //donüş tarihi
            );
            // Çift yönlü uçuşları FlightResponse listesine dönüştür
            List<FlightResponse> roundTripFlightResponses = roundTripFlights.stream()
                    .map(this::mapToFlightResponse)
                    .toList();

            searchResponse.setRoundTripFlights(roundTripFlightResponses);
        }

        return searchResponse;
    }


    //region mapping methods
    private FlightResponse mapToFlightResponse(Flight flight) {
        return FlightResponse.builder()
                .id(flight.getId())
                .departureAirport(mapToAirportResponse(flight.getDepartureAirport()))
                .arrivalAirport(mapToAirportResponse(flight.getArrivalAirport()))
                .departureDateTime(flight.getDepartureDateTime())
                .returnDateTime(flight.getReturnDateTime())
                .price(flight.getPrice())
                .build();
    }

    private Flight mapToFlight(FlightRequest flightRequest) {
        return Flight.builder()
                .departureAirport(mapToAirport(Objects.requireNonNull(airportService.getAirportById(flightRequest
                        .getDepartureAirportId()).orElseThrow(() -> new ResourceNotFoundException("departure airport not found")))))
                .arrivalAirport(mapToAirport(Objects.requireNonNull(airportService
                        .getAirportById(flightRequest.getArrivalAirportId())
                        .orElseThrow(() -> new ResourceNotFoundException("arrival airport not found")))))
                .departureDateTime(flightRequest.getDepartureDateTime())
                .returnDateTime(flightRequest.getReturnDateTime())
                .price(flightRequest.getPrice())
                .build();
    }

    private Airport mapToAirport(AirportResponse airportResponse) {
        Airport airport = new Airport();
        airport.setId(airportResponse.getId());
        airport.setCity(airportResponse.getCity());
        return airport;
    }

    private void mapToFlightFromRequest(FlightRequest flightRequest, Flight flight) {
        flight.setDepartureAirport(mapToAirport(Objects.requireNonNull(airportService.getAirportById(flightRequest
                .getDepartureAirportId()).orElseThrow(() -> new ResourceNotFoundException("departure airport not found")))));
        flight.setArrivalAirport(mapToAirport(Objects.requireNonNull(airportService
                .getAirportById(flightRequest.getArrivalAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("arrival airport not found")))));
        flight.setDepartureDateTime(flightRequest.getDepartureDateTime());
        flight.setReturnDateTime(flightRequest.getReturnDateTime());
        flight.setPrice(flightRequest.getPrice());
    }

    private AirportResponse mapToAirportResponse(Airport airport) {
        AirportResponse response = new AirportResponse();
        response.setId(airport.getId());
        response.setCity(airport.getCity());
        return response;
    }

    //endregion
}
