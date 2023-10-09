package com.example.groupservice.service;

import com.example.groupservice.dto.GroupDto;
import com.example.groupservice.dto.PostDto;
import com.example.groupservice.entity.Grouping;
import com.example.groupservice.exception.GroupNotExists;

import java.util.List;

public interface GroupService {

    GroupDto createGroup(Grouping grouping)throws GroupNotExists;
     List<GroupDto> getGroups();

    List<PostDto> getPostsForGroupId(Long id) throws GroupNotExists;

    void deleteGroup(Long id)throws GroupNotExists,Exception;


}
