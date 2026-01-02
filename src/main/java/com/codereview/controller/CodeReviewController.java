package com.codereview.controller;

import com.codereview.model.AnalysisRequest;
import com.codereview.model.AnalysisResponse;
import com.codereview.service.CodeAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CodeReviewController {

    @Autowired
    private CodeAnalyzerService analyzerService;

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

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Code Review Service is running!");
    }
}