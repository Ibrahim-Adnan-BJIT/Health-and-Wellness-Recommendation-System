package com.example.groupservice.controller;

import com.example.groupservice.dto.InteractionDto;
import com.example.groupservice.dto.PostDto;
import com.example.groupservice.entity.Posting;
import com.example.groupservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(@RequestBody Posting posting)
    {
        PostDto postDto1=postService.createPost(posting);
        return new ResponseEntity<>(postDto1, HttpStatus.CREATED);
    }
    @GetMapping("/getAllPosts")
    public ResponseEntity<List<PostDto>> getAllPosts()
    {
        List<PostDto>postDtos=postService.getAllPosts();
        return new ResponseEntity<>(postDtos,HttpStatus.FOUND);
    }
  /*  @GetMapping("/getAllInteraction/{id}")
    public ResponseEntity<List<InteractionDto>> getAllInteraction(@PathVariable Long id)
    {
        List<InteractionDto>interactionDtos=postService.getAllCommentsById(id);
        return new ResponseEntity<>(interactionDtos,HttpStatus.ACCEPTED);
    }*/
    @GetMapping("/getAllPostsByUserId")
    public ResponseEntity<List<PostDto>> getAllPostsByUserId()
    {
        List<PostDto>postDtos=postService.getAllPostsByUserId();
        return new ResponseEntity<>(postDtos,HttpStatus.FOUND);
    }

    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) throws Exception {
        postService.deletePost(id);
        return new ResponseEntity<>("Post Deleted SuccessFully",HttpStatus.GONE);
    }

}