package com.example.forum.repos;

import com.example.forum.models.Threads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreadRepo extends JpaRepository<Threads, Long> {
    List<Threads> findAllByOrderByIdDesc();
}
