package com.example.groupservice.controller;

import com.example.groupservice.dto.InteractionDto;
import com.example.groupservice.entity.Interaction;
import com.example.groupservice.service.InteractionService;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/interaction")
public class InteractionController {
    @Autowired
    private InteractionService interactionService;

    @PostMapping("/create")
    public ResponseEntity<InteractionDto> createInteraction(@RequestBody Interaction interaction)
    {
        InteractionDto interactionDto=interactionService.createInteraction(interaction);
        return new ResponseEntity<>(interactionDto, HttpStatus.CREATED);
    }
    @GetMapping("/getAllInteractionByPostId/{id}")
    public ResponseEntity<List<InteractionDto>> getAllInteraction(@PathVariable Long id)
    {
        List<InteractionDto>interactionDtos=interactionService.getAllInteraction(id);
        return new ResponseEntity<>(interactionDtos,HttpStatus.FOUND);
    }
    @DeleteMapping("/deleteInteraction/{id}")
    public ResponseEntity<String> deleteInteraction(@PathVariable Long id) throws Exception {
        interactionService.deleteInteraction(id);
        return new ResponseEntity<>("Deleted SuccessFully",HttpStatus.GONE);
    }
}
