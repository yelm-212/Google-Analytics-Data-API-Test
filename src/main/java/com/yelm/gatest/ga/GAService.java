package com.yelm.gatest.ga;

import com.google.analytics.data.v1beta.*;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.yelm.gatest.ga.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class GAService {
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private BetaAnalyticsDataClient analyticsData;

    public List<JSONObject> dateHourAndVisitedUserResponse(String startDate, String endDate) {
        RunReportRequest request =
                RunReportRequest.newBuilder()
                        .setProperty("properties/" + appConfig.getGaPropertyId())
                        .addDimensions(Dimension.newBuilder().setName("dateHour"))
                        .addMetrics(Metric.newBuilder().setName("activeUsers"))
                        .addMetrics(Metric.newBuilder().setName("totalUsers"))
                        .addDateRanges(DateRange.newBuilder().setStartDate(startDate).setEndDate(endDate))
                        .build();

        // Make the request.
        RunReportResponse response = analyticsData.runReport(request);

        return getSortedJsonResponsesDateHour(response);
    }

    public List<JSONObject> dateAndVisitedUserResponse(String startDate, String endDate) {
        RunReportRequest request =
                RunReportRequest.newBuilder()
                        .setProperty("properties/" + appConfig.getGaPropertyId())
                        .addDimensions(Dimension.newBuilder().setName("date"))
                        .addMetrics(Metric.newBuilder().setName("activeUsers"))
                        .addMetrics(Metric.newBuilder().setName("totalUsers"))
                        .addDateRanges(DateRange.newBuilder().setStartDate(startDate).setEndDate(endDate))
                        .build();

        // Make the request.
        RunReportResponse response = analyticsData.runReport(request);

        return getSortedJsonResponses(response);
    }

    public List<JSONObject> dateHourAndResponse(String startDate, String endDate) {
        RunReportRequest request =
                RunReportRequest.newBuilder()
                        .setProperty("properties/" + appConfig.getGaPropertyId())
                        .addDimensions(Dimension.newBuilder().setName("dateHour"))
                        .addDimensions(Dimension.newBuilder().setName("deviceCategory"))
                        .addMetrics(Metric.newBuilder().setName("screenPageViews"))
                        .addDateRanges(DateRange.newBuilder().setStartDate(startDate).setEndDate(endDate))
                        .build();

        // Make the request.
        RunReportResponse response = analyticsData.runReport(request);

        return getSortedJsonResponsesDateHour(response);
    }

    public List<JSONObject> dateAndResponse(String startDate, String endDate) {
        RunReportRequest request =
                RunReportRequest.newBuilder()
                        .setProperty("properties/" + appConfig.getGaPropertyId())
                        .addDimensions(Dimension.newBuilder().setName("date"))
                        .addDimensions(Dimension.newBuilder().setName("deviceCategory"))
                        .addMetrics(Metric.newBuilder().setName("screenPageViews"))
                        .addDateRanges(DateRange.newBuilder().setStartDate(startDate).setEndDate(endDate))
                        .build();

        // Make the request.
        RunReportResponse response = analyticsData.runReport(request);

        return getSortedJsonResponses(response);
    }

    public List<JSONObject> dateAndSessionSourceUserResponse(String startDate, String endDate) {

        RunReportRequest request = RunReportRequest.newBuilder()
                    .setProperty("properties/" + appConfig.getGaPropertyId())
                    .addDimensions(Dimension.newBuilder().setName("sessionSource"))
                    .addDimensions(Dimension.newBuilder().setName("deviceCategory"))
                    .addMetrics(Metric.newBuilder().setName("screenPageViews"))
                    .addDateRanges(DateRange.newBuilder().setStartDate(startDate).setEndDate(endDate))
                    .build();

        // Make the request.
        RunReportResponse response = analyticsData.runReport(request);

        return getSortedJsonResponses(response);
    }

    private List<JSONObject> getJsonResponses(RunReportResponse response) {
        // Extract dimension and metric headers
        List<DimensionHeader> dimensionHeaders = response.getDimensionHeadersList();
        List<MetricHeader> metricHeaders = response.getMetricHeadersList();

        // Create JSON response object
        List<JSONObject> jsonResponses = new ArrayList<>();

        for (Row row : response.getRowsList()) {
            JSONObject jsonResponse = new JSONObject();

            // Add dimension headers to JSON response
            for (int i = 0; i < dimensionHeaders.size(); i++) {
                String dimensionHeaderValue = row.getDimensionValues(i).getValue();
                jsonResponse.appendField(dimensionHeaders.get(i).getName(), dimensionHeaderValue);
            }

            // Add metric headers to JSON response
            for (int i = 0; i < metricHeaders.size(); i++) {
                String metricHeaderValue = row.getMetricValues(i).getValue();
                jsonResponse.appendField(metricHeaders.get(i).getName(), metricHeaderValue);
            }

            jsonResponses.add(jsonResponse);
        }

        return jsonResponses;
    }

    public List<JSONObject> getSortedJsonResponses(RunReportResponse response) {
        List<JSONObject> jsonResponses = getJsonResponses(response);

        // date 기준으로 정렬
        jsonResponses.sort(Comparator.comparing(jsonResponse -> {
            String dateString = jsonResponse.getAsString("date");
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyyMMdd"));
            return date;
        }));

        return jsonResponses;
    }

    public List<JSONObject> getSortedJsonResponsesDateHour(RunReportResponse response) {
        List<JSONObject> jsonResponses = getJsonResponses(response);

        // dateHour 기준으로 정렬
        jsonResponses.sort(Comparator.comparing(jsonResponse -> {
            String dateString = jsonResponse.getAsString("dateHour");
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyyMMddhhmm"));
            return date;
        }));

        return jsonResponses;
    }



}
