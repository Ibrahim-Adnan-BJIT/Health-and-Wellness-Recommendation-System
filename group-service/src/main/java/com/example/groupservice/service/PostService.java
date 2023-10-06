package com.example.groupservice.service;

import com.example.groupservice.dto.InteractionDto;
import com.example.groupservice.dto.PostDto;
import com.example.groupservice.entity.Interaction;
import com.example.groupservice.entity.Posting;
import com.example.groupservice.exception.GroupNotExists;
import com.example.groupservice.exception.PostIdNotExists;

import java.util.List;

public interface PostService {

    PostDto createPost(Posting posting)throws GroupNotExists, PostIdNotExists;
    List<PostDto> getAllPosts();

   // List<InteractionDto> getAllCommentsById(Long id) throws PostIdNotExists;
    List<PostDto>getAllPostsByUserId();
    void deletePost(Long id) throws PostIdNotExists,Exception;
}
