package com.lam.migrationservice.scheduler.configuration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "migration")
public class MigrationProps {
    private String zone;
    private String cron;
    private ServiceConfig client;
    private ServiceConfig note;

    @Data
    public static class ServiceConfig {
        private String server;
        private String url;
        private int port;
    }
}
