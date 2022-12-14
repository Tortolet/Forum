package com.example.forum.repos;

import com.example.forum.models.Themes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepo extends JpaRepository<Themes, Long> {
}
