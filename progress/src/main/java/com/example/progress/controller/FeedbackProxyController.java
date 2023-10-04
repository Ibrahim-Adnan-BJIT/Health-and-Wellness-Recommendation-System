package com.example.progress.controller;

import com.example.progress.dto.request.FeedbackRequestDTO;
import com.example.progress.dto.response.FeedbackResponseDTO;
import com.example.progress.repository.SleepFeedbackRepository;
import com.example.progress.response.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v2/feedback")
public class FeedbackProxyController {
    @GetMapping("/get")
    public List<SleepFeedbackRepository> getSleepFeedback() {

        return null;
    }
}
