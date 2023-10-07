package com.example.groupservice.service.impl;

import com.example.groupservice.dto.GroupAndUserDto;
import com.example.groupservice.dto.InteractionDto;
import com.example.groupservice.entity.GroupUser;
import com.example.groupservice.entity.Interaction;
import com.example.groupservice.entity.Posting;
import com.example.groupservice.exception.CommentNull;
import com.example.groupservice.exception.GroupNotExists;
import com.example.groupservice.exception.PostIdNotExists;
import com.example.groupservice.repository.GroupUserRepo;
import com.example.groupservice.repository.InteractionRepo;
import com.example.groupservice.repository.PostRepo;
import com.example.groupservice.service.InteractionService;
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
public class InteractionServiceImpl implements InteractionService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private InteractionRepo interactionRepo;

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private GroupUserRepo groupUserRepo;
    @Override
    public InteractionDto createInteraction(Interaction interaction)throws CommentNull, PostIdNotExists {
            if(interaction.getComment()==null)
                throw new CommentNull("Please write something before interacting with someone");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id =  Long.parseLong(authentication.getName());

        Optional<Posting> posting=postRepo.findById(interaction.getPosting().getId());


        if(posting.isEmpty())
        {
            throw new PostIdNotExists("Invalid PostId");
        }

        String groupName=posting.get().getGroupName();
        interaction.setPosting(posting.get());
        interaction.setUserId(id);
       interactionRepo.save(interaction);
       InteractionDto interactionDto= modelMapper.map(interaction,InteractionDto.class);
      // interactionDto.setGroupName(posting.get().getGroupName());
       return interactionDto;
    }

    @Override
    public List<InteractionDto> getAllInteraction(Long id) throws PostIdNotExists ,GroupNotExists{
        Optional<Posting> posting= Optional.ofNullable(postRepo.findById(id).orElseThrow(() ->
                new PostIdNotExists("Invalid Post Id")));

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
        List<Interaction>interactions=posting.get().getInteractions();

        return interactions.stream().map((todo) -> modelMapper.map(todo, InteractionDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public void deleteInteraction(Long id)throws CommentNull,Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String targetRole = "ROLE_USER";
        long userId =  Long.parseLong(authentication.getName());
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        boolean hasTargetRole = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals(targetRole));
        Interaction interaction=interactionRepo.findById(id).orElseThrow(()->
                new CommentNull("Invalid Comment Id"));
        if(interaction.getUserId()!=userId && hasTargetRole)
        {
            throw new Exception("You cant delete someones Comment");
        }
        interactionRepo.deleteById(id);
    }
}
