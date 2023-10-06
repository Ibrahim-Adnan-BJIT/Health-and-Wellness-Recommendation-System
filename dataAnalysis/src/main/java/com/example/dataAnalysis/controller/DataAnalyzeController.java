package com.example.dataAnalysis.controller;

import com.example.dataAnalysis.response.ResponseHandler;
import com.example.dataAnalysis.service.IDataAnalysisService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dataAnalysis")
@AllArgsConstructor
public class DataAnalyzeController {
    private final IDataAnalysisService dataAnalysisService;

    @GetMapping("/analyze")
    public ResponseEntity<?> analyzeData(){
        return ResponseHandler.generateResponse("Analyzed Data", HttpStatus.OK,
                dataAnalysisService.dataAnalysis());
    }
}
