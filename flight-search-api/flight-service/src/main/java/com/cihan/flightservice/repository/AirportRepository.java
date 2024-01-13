package com.cihan.flightservice.repository;

import com.cihan.flightservice.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    boolean existsByCityIgnoreCase(String city);

}
