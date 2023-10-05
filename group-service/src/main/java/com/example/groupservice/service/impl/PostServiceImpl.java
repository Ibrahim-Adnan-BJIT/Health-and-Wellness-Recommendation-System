package com.example.groupservice.service.impl;

import com.example.groupservice.dto.GroupAndUserDto;
import com.example.groupservice.dto.InteractionDto;
import com.example.groupservice.dto.PostDto;
import com.example.groupservice.entity.GroupUser;
import com.example.groupservice.entity.Grouping;
import com.example.groupservice.entity.Interaction;
import com.example.groupservice.entity.Posting;
import com.example.groupservice.exception.GroupNotExists;
import com.example.groupservice.exception.PostIdNotExists;
import com.example.groupservice.repository.GroupRepo;
import com.example.groupservice.repository.GroupUserRepo;
import com.example.groupservice.repository.PostRepo;
import com.example.groupservice.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;*/
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private GroupUserRepo groupUserRepo;
    @Override
    public PostDto createPost(Posting posting) throws GroupNotExists, PostIdNotExists {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id =  Long.parseLong(authentication.getName());

        String groupName=posting.getGroupName();
       Optional<Grouping> grouping= Optional.ofNullable(Optional.ofNullable(groupRepo.findByName(groupName)).orElseThrow(() ->
               new GroupNotExists("Invalid Group Name")));

        Optional<GroupUser> groupUser= Optional.ofNullable(groupUserRepo.findByUserIdAndGroupId(id, grouping.get().getId()));
        if(groupUser.isEmpty())
        {
            throw new GroupNotExists("This user didnt joined this group before so Please Join before commenting");
        }
        if(posting.getPostDescription()==null)
        {
            throw new PostIdNotExists("Please Write something before posting");
        }
        posting.setUserId(id);
        posting.setGrouping(grouping.get());
        postRepo.save(posting);
     return modelMapper.map(posting,PostDto.class);
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Posting> postings=postRepo.findAll();
        return postings.stream().map((todo) -> modelMapper.map(todo, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<InteractionDto> getAllCommentsById(Long id) throws PostIdNotExists {
        Optional<Posting> postings= Optional.ofNullable(postRepo.findById(id).orElseThrow(() ->
                new PostIdNotExists("Invalid Post Id")));

        List<Interaction>interactions=postings.get().getInteractions();
      return interactions.stream().map((todo) -> modelMapper.map(todo, InteractionDto.class))
                .collect(Collectors.toList());
    }

}
