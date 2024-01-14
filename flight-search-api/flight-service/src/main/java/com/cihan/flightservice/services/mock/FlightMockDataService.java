package com.cihan.flightservice.services.mock;

import com.cihan.flightservice.dtos.FlightRequest;
import com.cihan.flightservice.services.FlightService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightMockDataService {
    private final FlightService flightService;

    public FlightMockDataService(FlightService flightService) {
        this.flightService = flightService;
    }

    public void generateAndSaveFlightMockData() {
        // Burada mock verileri oluşturun, örneğin bir liste içinde FlightRequest nesneleri
        List<FlightRequest> mockFlightRequests = generateMockFlightRequests();

        for (FlightRequest mockFlightRequest : mockFlightRequests) {
            flightService.saveFlight(mockFlightRequest);
        }
    }

    private List<FlightRequest> generateMockFlightRequests() {
        List<FlightRequest> mockFlightRequests = new ArrayList<>();

        // Sabit havaalanı çifti
        Long departureAirportId = 1L;
        Long arrivalAirportId = 2L;

        // Her gün 10 Mock uçuş verilerini üret
        for (int i = 0; i < 10; i++) {
            LocalDateTime departureDateTime = LocalDateTime.now().plusDays(i);
            LocalDateTime returnDateTime = departureDateTime.plusHours(2);
            BigDecimal price = BigDecimal.valueOf(100 + i * 10);

            FlightRequest flightRequest = new FlightRequest();
            flightRequest.setDepartureAirportId(departureAirportId);
            flightRequest.setArrivalAirportId(arrivalAirportId);
            flightRequest.setDepartureDateTime(departureDateTime);
            flightRequest.setReturnDateTime(returnDateTime);
            flightRequest.setPrice(price);

            mockFlightRequests.add(flightRequest);
        }

        return mockFlightRequests;
    }

}
