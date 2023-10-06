package com.example.groupservice.repository;

import com.example.groupservice.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Posting,Long> {

    Posting findById(int id);
    List<Posting> findByUserId(Long id);
}
