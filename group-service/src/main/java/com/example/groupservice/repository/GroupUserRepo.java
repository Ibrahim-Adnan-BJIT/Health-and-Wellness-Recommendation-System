package com.example.groupservice.repository;

import com.example.groupservice.dto.GroupAndUserDto;
import com.example.groupservice.entity.GroupUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupUserRepo extends JpaRepository<GroupUser, Long> {

//    @Query("SELECT CASE WHEN COUNT(ug) > 0 THEN true ELSE false END " +
//            "FROM GroupAndUserDto ug WHERE ug.userId = :userId AND ug.groupId = :groupId")
    //boolean isUserInGrouping(@Param("userId") Long userId, @Param("groupId") Long groupId);

  /*  @Query("SELECT COUNT(gu) > 0 FROM GroupUser gu WHERE gu.userId = :userId AND gu.groupId = :groupId")
    boolean isUserInGrouping(@Param("userId") Long userId, @Param("groupId") Long groupId);*/

    GroupUser findByUserIdAndGroupId(long userId,long groupId);

    List<GroupUser> findByGroupId(Long id);
    List<GroupUser>findByUserId(Long id);
}
