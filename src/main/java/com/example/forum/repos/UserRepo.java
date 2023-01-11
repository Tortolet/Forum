package com.example.forum.repos;

import com.example.forum.models.Threads;
import com.example.forum.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

    long countByThreads(Threads threads);

    @Query(nativeQuery = true, value = "SELECT thread_id as test FROM users_threads_likes GROUP BY thread_id ORDER BY COUNT(thread_id) DESC LIMIT 1")
    String showMaxLikePost();
}
