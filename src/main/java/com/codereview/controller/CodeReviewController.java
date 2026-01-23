package com.codereview.controller;

import com.codereview.model.AnalysisRequest;
import com.codereview.model.AnalysisResponse;
import com.codereview.model.CodeMetrics;
import com.codereview.service.CodeAnalyzerService;
import com.codereview.service.CodeMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CodeReviewController {

    @Autowired
    private CodeAnalyzerService analyzerService;

    @Autowired
    private CodeMetricsService metricsService; // NEW! Metrics service inject kiya

    @PostMapping("/analyze")
    public ResponseEntity<AnalysisResponse> analyzeCode(@RequestBody AnalysisRequest request) {
        try {
            AnalysisResponse response = analyzerService.analyzeCode(request.getCode());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            AnalysisResponse errorResponse = new AnalysisResponse();
            errorResponse.setError("Analysis failed: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    // NEW ENDPOINT! Metrics calculate karne ke liye
    @PostMapping("/metrics")
    public ResponseEntity<CodeMetrics> getCodeMetrics(@RequestBody AnalysisRequest request) {
        try {
            CodeMetrics metrics = metricsService.calculateMetrics(request.getCode());
            return ResponseEntity.ok(metrics);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new CodeMetrics());
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Code Review Service is running!");
    }
}