package com.yelm.gatest.ga.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AppConfig {
    /*
     * GoogleAnalytics
     */
    @Value("${google.service_account.mail}")
    private String gaMail;

    @Value("${google.analytics.property_id}")
    private String gaPropertyId;

    @Value("${google.api_key}")
    private String api_key;
}