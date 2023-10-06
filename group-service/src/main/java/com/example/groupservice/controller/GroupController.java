package com.example.groupservice.controller;

import com.example.groupservice.dto.GroupAndUserDto;
import com.example.groupservice.dto.GroupDto;
import com.example.groupservice.dto.InteractionDto;
import com.example.groupservice.dto.PostDto;
import com.example.groupservice.entity.GroupUser;
import com.example.groupservice.entity.Grouping;
import com.example.groupservice.entity.Posting;
import com.example.groupservice.service.GroupService;
import com.example.groupservice.service.GroupUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/group")
public class GroupController {

    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupUserService groupUserService;
    @PostMapping("/create")
    public ResponseEntity<GroupDto> createGroup(@RequestBody Grouping grouping)
    {
        GroupDto groupDto=groupService.createGroup(grouping);
        return new ResponseEntity<>(groupDto, HttpStatus.CREATED);
    }

    @PostMapping("/join")
    public ResponseEntity<GroupAndUserDto> joinGroup(@RequestBody GroupUser groupUser)
    {
       GroupAndUserDto groupAndUserDto= groupUserService.joinGroup(groupUser);
       return new ResponseEntity<>(groupAndUserDto,HttpStatus.CREATED);
    }

    @GetMapping("/getAllGroups")
    public ResponseEntity<List<GroupDto>> getAllGroups()
    {
        List<GroupDto> groupDtos=groupService.getGroups();
        return new ResponseEntity<>(groupDtos,HttpStatus.FOUND);
    }

    @GetMapping("/GetAllGroupsAndUsers")
    public ResponseEntity<List<GroupAndUserDto>> getAllGroupAndUsers()
    {
        List<GroupAndUserDto> groupAndUserDtos=groupUserService.getUsers();
        return new ResponseEntity<>(groupAndUserDtos,HttpStatus.FOUND);
    }

    @GetMapping("/getAllPostsWithGroupId/{id}")
    public ResponseEntity<List<PostDto>> getAllPostsWithGroupId(@PathVariable Long id)
    {
        List<PostDto> postDtos=groupService.getPostsForGroupId(id);
        return new ResponseEntity<>(postDtos,HttpStatus.ACCEPTED);
    }

    @GetMapping("/getAllUsersWithGroupId/{id}")
    public ResponseEntity<List<GroupAndUserDto>> getAllUsersWithGroupId(@PathVariable Long id)
    {
        List<GroupAndUserDto> userDtos=groupUserService.getAllUserWithGroupId(id);
        return new ResponseEntity<>(userDtos,HttpStatus.FOUND);
    }

    @GetMapping("/getAllGroupByUserId")
    public ResponseEntity<List<GroupAndUserDto>> getAllGroupByUserId()
    {
        List<GroupAndUserDto> groupAndUserDtos=groupUserService.getAllGroupWithUserId();
        return new ResponseEntity<>(groupAndUserDtos,HttpStatus.FOUND);
    }

    @DeleteMapping("/deleteGroup/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable Long id) throws Exception
    {
        groupService.deleteGroup(id);
        return new ResponseEntity<>("Group Deleted SuccessFully",HttpStatus.GONE);
    }

}
