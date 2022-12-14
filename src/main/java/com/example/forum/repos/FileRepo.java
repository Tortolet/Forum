package com.example.forum.repos;

import com.example.forum.models.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepo extends JpaRepository<Files, Long> {
}
