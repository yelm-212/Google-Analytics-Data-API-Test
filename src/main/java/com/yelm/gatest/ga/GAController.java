package com.yelm.gatest.ga;


import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/ga")
public class GAController {
    private final GAService gaService;

    public GAController(GAService gaService) {
        this.gaService = gaService;
    }

    @GetMapping("/pageview/today")
    public ResponseEntity toToday() throws IOException {
        List<JSONObject> jsonResponse = gaService.dateHourAndResponse("yesterday", "today");

        return ResponseEntity.ok()
                .body(jsonResponse);
    }

    @GetMapping("/pageview/5daysago")
    public ResponseEntity to5daysAgo() throws IOException {
        LocalDate now = LocalDate.now();
        String startDate = String.valueOf(now.minusDays(5));
        String endDate = String.valueOf(now.minusDays(1));

        List<JSONObject> jsonResponse = gaService.dateAndResponse(startDate, endDate);
        return ResponseEntity.ok()
                .body(jsonResponse);
    }

    @GetMapping("/pageview/7daysago")
    public ResponseEntity to7daysAgo() throws IOException {
        List<JSONObject> jsonResponse = gaService.dateAndResponse("7daysAgo", "yesterday");

        return ResponseEntity.ok()
                .body(jsonResponse);
    }

    @GetMapping("/pageview/28daysago")
    public ResponseEntity to28daysAgo() throws IOException {
        LocalDate now = LocalDate.now();
        String startDate = String.valueOf(now.minusDays(28));
        String endDate = String.valueOf(now.minusDays(1));

        List<JSONObject> jsonResponse = gaService.dateAndResponse(startDate, endDate);

        return ResponseEntity.ok()
                .body(jsonResponse);
    }

    @GetMapping("/pageview/3monthsago")
    public ResponseEntity to3monthsAgo() throws IOException {
        LocalDate now = LocalDate.now();
        String startDate = String.valueOf(now.minusMonths(3));
        String endDate = String.valueOf(now.minusDays(1));

        List<JSONObject> jsonResponse = gaService.dateAndResponse(startDate, endDate);

        return ResponseEntity.ok()
                .body(jsonResponse);
    }

    // 기간을 입력받아 처리할 수도 있다.
    @GetMapping("/pageview/custom")
    public ResponseEntity customStartEndDate(
            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("startdate") String startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("enddate") String endDate) throws IOException {
        List<JSONObject> jsonResponse = gaService.dateAndResponse(startDate, endDate);

        return ResponseEntity.ok()
                .body(jsonResponse);
    }

    @GetMapping("/visitors/today")
    public ResponseEntity viToday() throws IOException {
        List<JSONObject> jsonResponse = gaService.dateHourAndVisitedUserResponse("yesterday", "today");

        return ResponseEntity.ok()
                .body(jsonResponse);
    }

    @GetMapping("/visitors/5daysago")
    public ResponseEntity vi5daysago() throws IOException {
        LocalDate now = LocalDate.now();
        String startDate = String.valueOf(now.minusDays(5));
        String endDate = String.valueOf(now.minusDays(1));
        List<JSONObject> jsonResponse = gaService.dateAndVisitedUserResponse(startDate, endDate);

        return ResponseEntity.ok()
                .body(jsonResponse);
    }

    @GetMapping("/visitors/7daysago")
    public ResponseEntity vi7daysago() throws IOException {
        List<JSONObject> jsonResponse = gaService.dateAndVisitedUserResponse("7daysAgo", "yesterday");

        return ResponseEntity.ok()
                .body(jsonResponse);
    }

    @GetMapping("/visitors/28daysago")
    public ResponseEntity vi28daysago() throws IOException {
        LocalDate now = LocalDate.now();
        String startDate = String.valueOf(now.minusDays(28));
        String endDate = String.valueOf(now.minusDays(1));
        List<JSONObject> jsonResponse = gaService.dateAndVisitedUserResponse(startDate, endDate);

        return ResponseEntity.ok()
                .body(jsonResponse);
    }

    @GetMapping("/visitors/3monthsago")
    public ResponseEntity vi3monthsago() throws IOException {
        LocalDate now = LocalDate.now();
        String startDate = String.valueOf(now.minusMonths(3));
        String endDate = String.valueOf(now.minusDays(1));
        List<JSONObject> jsonResponse = gaService.dateAndVisitedUserResponse(startDate, endDate);

        return ResponseEntity.ok()
                .body(jsonResponse);
    }

    @GetMapping("/visitors/custom")
    public ResponseEntity viCustomStartEndDate(
            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("startdate") String startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("enddate") String endDate) throws IOException {
        List<JSONObject> jsonResponse = gaService.dateAndVisitedUserResponse(startDate, endDate);

        return ResponseEntity.ok()
                .body(jsonResponse);
    }

    @GetMapping("/sessionurl/custom")
    public ResponseEntity sessionSourceCustomStartEndDate(
            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("startdate") String startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("enddate") String endDate) throws IOException {
        List<JSONObject> jsonResponse = gaService.dateAndSessionSourceUserResponse(startDate, endDate);

        return ResponseEntity.ok()
                .body(jsonResponse);
    }

}
