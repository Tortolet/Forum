package com.example.forum.repos;

import com.example.forum.models.Threads;
import com.example.forum.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

    long countByThreads(Threads threads);
}
