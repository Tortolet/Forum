package com.example.forum.repos;

import com.example.forum.models.Comments;
import com.example.forum.models.Threads;
import com.example.forum.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

    long countByThreads(Threads threads);

    long countByComments(Comments comments);

    @Query(nativeQuery = true, value = "SELECT thread_id as test FROM users_threads_likes GROUP BY thread_id ORDER BY COUNT(thread_id) DESC LIMIT 1")
    String showMaxLikePost();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE from users_threads_likes where thread_id = :id")
    void deleteLikesPost(@Param("id") long id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE from users_comments_likes where comment_id = :id")
    void deleteLikesComment(@Param("id") long id);
}
