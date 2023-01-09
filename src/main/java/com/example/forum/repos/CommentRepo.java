package com.example.forum.repos;

import com.example.forum.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comments, Long> {
    List<Comments> findByThreads_IdOrderByIdDesc(Long id);

    @Query("select count(c) from Comments c where c.users.id = :userId")
    long countFirstBy(@Param("userId") long id);
}
