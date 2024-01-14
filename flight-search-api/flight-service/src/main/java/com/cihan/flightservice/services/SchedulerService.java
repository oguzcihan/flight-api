package com.cihan.flightservice.services;

import com.cihan.flightservice.services.mock.FlightMockDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SchedulerService {
    private final FlightMockDataService flightMockDataService;

    public SchedulerService(FlightMockDataService flightMockDataService) {
        this.flightMockDataService = flightMockDataService;
    }

    /**
     * Her gün saat 2.00 de çağırılır.
     */
//    @Scheduled(cron = "0 0 2 * * ?")
    public void fetchDataAndSaveDatabase() {
        log.info("Fetching and saving flight mock data...");
        flightMockDataService.generateAndSaveFlightMockData();
    }

}
