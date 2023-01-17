package com.example.forum.repos;

import com.example.forum.models.Threads;
import com.example.forum.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreadRepo extends JpaRepository<Threads, Long> {
    List<Threads> findAllByOrderByIdDesc();

    List<Threads> findByUsers(Users users);

    @Query("select count(t) from Threads t where t.users.id = :userId")
    long countFirstBy(@Param("userId") long id);
}
