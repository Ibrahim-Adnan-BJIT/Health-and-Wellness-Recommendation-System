package com.example.groupservice.service.impl;

import com.example.groupservice.dto.GroupDto;
import com.example.groupservice.dto.PostDto;
import com.example.groupservice.entity.GroupUser;
import com.example.groupservice.entity.Grouping;
import com.example.groupservice.entity.Posting;
import com.example.groupservice.exception.GroupNotExists;
import com.example.groupservice.repository.GroupRepo;
import com.example.groupservice.repository.GroupUserRepo;
import com.example.groupservice.service.GroupService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private GroupUserRepo groupUserRepo;

    @Override
    public GroupDto createGroup(Grouping grouping) throws GroupNotExists {
        if (grouping.getName() == null || grouping.getDescription() == null) {
            throw new GroupNotExists("Group Details Cant be null");
        }
        groupRepo.save(grouping);
        return modelMapper.map(grouping, GroupDto.class);
    }

    @Override
    public List<GroupDto> getGroups() {
        List<Grouping> groupDtos = groupRepo.findAll();
        return groupDtos.stream().map((todo) -> modelMapper.map(todo, GroupDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public List<PostDto> getPostsForGroupId(Long id) throws GroupNotExists {
        Optional<Grouping> groupings = Optional.ofNullable(groupRepo.findById(id).orElseThrow(() ->
                new GroupNotExists("Invalid Group id")));
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


            List<Posting> postings = new ArrayList<>();
            Grouping grouping = groupings.get();
            postings = grouping.getPostings();

            return postings.stream().map((todo) -> modelMapper.map(todo, PostDto.class))
                    .collect(Collectors.toList());
        }

    @Override
    public void deleteGroup(Long id) throws GroupNotExists, Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String targetRole = "ROLE_USER";
        long userId =  Long.parseLong(authentication.getName());
        authentication.getAuthorities();
        // L=authentication.getAuthorities();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        boolean hasTargetRole = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals(targetRole));

        if (hasTargetRole) {
            throw new Exception("Only Admin can delete Groups");
        }
        Grouping grouping=groupRepo.findById(id).orElseThrow(()->
                new GroupNotExists("Invalid Group Id"));
        groupRepo.deleteById(id);
        List<GroupUser>groupUsers=groupUserRepo.findByGroupId(id);
        for (GroupUser g1:groupUsers)
        {
            groupUserRepo.deleteById(g1.getId());
        }
    }


}

