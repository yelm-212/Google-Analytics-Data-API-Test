package com.yelm.gatest.ga.test;

import com.google.analytics.data.v1beta.*;

public class QuickstartSample {
    private static String propertyId = "Your_Property_ID";

    public static void main(String... args) throws Exception {
        sampleRunReport(propertyId);
    }

    // This is an example snippet that calls the Google Analytics Data API and runs a simple report
    // on the provided GA4 property id.
    static void sampleRunReport(String propertyId) throws Exception {
        // Using a default constructor instructs the client to use the credentials
        // specified in GOOGLE_APPLICATION_CREDENTIALS environment variable.
        try (BetaAnalyticsDataClient analyticsData = BetaAnalyticsDataClient.create()) {

            RunReportRequest request =
                    RunReportRequest.newBuilder()
                            .setProperty("properties/" + propertyId)
                            .addDimensions(Dimension.newBuilder().setName("city"))
                            .addMetrics(Metric.newBuilder().setName("activeUsers"))
                            .addDateRanges(DateRange.newBuilder().setStartDate("2020-03-31").setEndDate("today"))
                            .build();

            RunReportRequest newRequest =
                    RunReportRequest.newBuilder().build();

            // Make the request.
            RunReportResponse response = analyticsData.runReport(request);

            System.out.println("Report result:");
            // Iterate through every row of the API response.
            for (Row row : response.getRowsList()) {
                System.out.printf(
                        "%s, %s%n", row.getDimensionValues(0).getValue(), row.getMetricValues(0).getValue());
            }
        }
    }
}
