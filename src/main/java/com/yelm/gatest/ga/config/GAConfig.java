package com.yelm.gatest.ga.config;

import com.google.analytics.data.v1beta.BetaAnalyticsDataClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GAConfig {
    @Bean
    @SuppressWarnings("javadoc")
    public BetaAnalyticsDataClient analyticsData() throws IOException {
        return BetaAnalyticsDataClient.create();
    }
}
