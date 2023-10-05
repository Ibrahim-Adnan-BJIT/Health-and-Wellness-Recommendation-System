package com.example.dataAnalysis.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dataAnalysis")
@AllArgsConstructor
public class sdfsdfssd {

    @GetMapping("/get")
    public String getName(){
        return "sdfsdf";
    }
}
