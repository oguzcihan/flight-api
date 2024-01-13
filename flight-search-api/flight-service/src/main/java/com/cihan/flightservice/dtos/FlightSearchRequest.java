package com.cihan.flightservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightSearchRequest {
    private Long departureAirportId;
    private Long arrivalAirportId;
    private LocalDateTime departureDate;
    private LocalDateTime returnDate;
}
