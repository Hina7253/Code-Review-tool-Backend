// CodeAnalysisController.java
package com.codeanalyzer.controller;

import com.codeanalyzer.dto.AnalysisResponse;
import com.codeanalyzer.dto.CodeRequest;
import com.codeanalyzer.dto.MetricsResponse;
import com.codeanalyzer.service.CodeAnalysisService;
import com.codeanalyzer.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CodeAnalysisController {

    @Autowired
    private CodeAnalysisService analysisService;

    @Autowired
    private MetricsService metricsService;

    @PostMapping("/analyze")
    public ResponseEntity<AnalysisResponse> analyzeCode(@Valid @RequestBody CodeRequest request) {
        AnalysisResponse response = analysisService.analyzeCode(request.getCode());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/metrics")
    public ResponseEntity<MetricsResponse> getMetrics(@Valid @RequestBody CodeRequest request) {
        MetricsResponse response = metricsService.calculateMetrics(request.getCode());
        return ResponseEntity.ok(response);
    }
}