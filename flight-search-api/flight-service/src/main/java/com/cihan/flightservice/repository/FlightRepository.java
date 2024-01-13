package com.cihan.flightservice.repository;

import com.cihan.flightservice.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query("SELECT f FROM Flight f WHERE f.departureAirport.id = :departureAirportId " +
            "AND f.arrivalAirport.id = :arrivalAirportId " +
            "AND f.departureDateTime >= :departureDate")
    List<Flight> searchOneWayFlights(
            @Param("departureAirportId") Long departureAirportId,
            @Param("arrivalAirportId") Long arrivalAirportId,
            @Param("departureDate") LocalDateTime departureDate
    );

    @Query("SELECT f FROM Flight f WHERE f.departureAirport.id = :departureAirportId " +
            "AND f.arrivalAirport.id = :arrivalAirportId " +
            "AND f.returnDateTime >= :returnDate")
    List<Flight> searchRoundTripFlights(
            @Param("departureAirportId") Long departureAirportId,
            @Param("arrivalAirportId") Long arrivalAirportId,
            @Param("returnDate") LocalDateTime returnDate
    );
}
