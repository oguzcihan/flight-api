package com.cihan.flightservice.config;

import com.cihan.flightservice.services.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Slf4j
public class SchedulerConfig {

    private final SchedulerService schedulerService;

    public SchedulerConfig(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }


    @Scheduled(cron = "${scheduled.task.cron.expression}")
    public void saveFlight() {
        log.info("Fetching and saving flight mock data started...");
        schedulerService.fetchDataAndSaveDatabase();
        log.info("Fetching and saving flight mock data finished...");
    }
}
