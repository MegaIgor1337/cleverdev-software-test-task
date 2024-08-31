package com.lam.migrationservice.scheduler;


import com.lam.migrationservice.scheduler.configuration.MigrationProps;
import com.lam.migrationservice.service.MigrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MigrationScheduler {
    @Autowired
    private MigrationProps migrationProps;

    @Autowired
    private MigrationService migrationService;
    @Scheduled(cron = "#{migrationProps.cron}", zone = "#{migrationProps.zone}")
    public void migrateNotes() {
        log.info("Start importing notes");
        migrationService.importNotes();
        log.info("End importing notes");
    }
}
