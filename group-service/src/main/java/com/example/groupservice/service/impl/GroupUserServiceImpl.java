package com.example.groupservice.service.impl;

import com.example.groupservice.dto.GroupAndUserDto;
import com.example.groupservice.dto.GroupDto;
import com.example.groupservice.entity.GroupUser;
import com.example.groupservice.entity.Grouping;
import com.example.groupservice.exception.GroupNotExists;
import com.example.groupservice.repository.GroupRepo;
import com.example.groupservice.repository.GroupUserRepo;
import com.example.groupservice.service.GroupService;
import com.example.groupservice.service.GroupUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;*/
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupUserServiceImpl implements GroupUserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GroupUserRepo groupUserRepo;
    @Autowired
    private GroupRepo groupRepo;



    @Override
    public GroupAndUserDto joinGroup(GroupUser groupUser)throws GroupNotExists {

        if(groupUser.getGroupId()==null)
            throw new GroupNotExists("Group id cant be null");
        Optional<Grouping> grouping=groupRepo.findById(groupUser.getGroupId());
        if(grouping.isEmpty())
            throw new GroupNotExists("Invalid GroupId");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id =  Long.parseLong(authentication.getName());
        groupUser.setUserId(id);
       Optional <GroupUser> groupUser1= Optional.ofNullable(groupUserRepo.findByUserIdAndGroupId(id, groupUser.getGroupId()));
       if(groupUser1.isPresent())
           throw new GroupNotExists("You are Already in this group");
       else
           groupUserRepo.save(groupUser);

        return modelMapper.map(groupUser,GroupAndUserDto.class);
    }

    @Override
    public List<GroupAndUserDto> getUsers() {
        List<GroupUser> groupUsers=groupUserRepo.findAll();
        return groupUsers.stream().map((todo) -> modelMapper.map(todo, GroupAndUserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupAndUserDto> getAllUserWithGroupId(Long id) throws GroupNotExists {
       List<GroupUser> groupUsers=groupUserRepo.findByGroupId(id);
       if(groupUsers.isEmpty())
       {
           throw new GroupNotExists("Invalid Group Id");
       }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String targetRole = "ROLE_USER";
        long userId =  Long.parseLong(authentication.getName());
        authentication.getAuthorities();
        // L=authentication.getAuthorities();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        boolean hasTargetRole = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals(targetRole));
        if (hasTargetRole) {
            GroupUser groupUser=groupUserRepo.findByUserIdAndGroupId(userId,id);
            if(groupUser==null)
            {
                throw new GroupNotExists("User is not in that group so please Join ...");
            }
        }

        return groupUsers.stream().map((todo) -> modelMapper.map(todo, GroupAndUserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupAndUserDto> getAllGroupWithUserId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id =  Long.parseLong(authentication.getName());
        List<GroupUser>groupUsers=groupUserRepo.findByUserId(id);
        return  groupUsers.stream().map((todo) -> modelMapper.map(todo, GroupAndUserDto.class))
                .collect(Collectors.toList());
    }

}
