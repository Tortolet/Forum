package com.example.forum.repos;

import com.example.forum.models.Comments;
import com.example.forum.models.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comments, Long> {
    List<Comments> findByThreads_Id(Long id);
}
