package com.example.groupservice.service;

import com.example.groupservice.dto.InteractionDto;
import com.example.groupservice.entity.Interaction;
import com.example.groupservice.exception.CommentNull;
import com.example.groupservice.exception.PostIdNotExists;

import java.util.List;

public interface InteractionService {

    InteractionDto createInteraction(Interaction interaction)throws CommentNull, PostIdNotExists;

    List<InteractionDto> getAllInteraction();
}
