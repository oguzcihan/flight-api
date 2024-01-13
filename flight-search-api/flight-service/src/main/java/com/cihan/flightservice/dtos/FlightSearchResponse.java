package com.cihan.flightservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightSearchResponse {
    private List<FlightResponse> oneWayFlights;
    private List<FlightResponse> roundTripFlights;
}
