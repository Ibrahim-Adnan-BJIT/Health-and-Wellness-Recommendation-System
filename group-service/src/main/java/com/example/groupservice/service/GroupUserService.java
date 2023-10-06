package com.example.groupservice.service;

import com.example.groupservice.dto.GroupAndUserDto;
import com.example.groupservice.entity.GroupUser;
import com.example.groupservice.exception.GroupNotExists;

import java.util.List;

public interface GroupUserService {

    GroupAndUserDto joinGroup(GroupUser groupUser)throws GroupNotExists;
    List<GroupAndUserDto> getUsers();
    List<GroupAndUserDto> getAllUserWithGroupId(Long id) throws  GroupNotExists;

    List<GroupAndUserDto>getAllGroupWithUserId();
}
