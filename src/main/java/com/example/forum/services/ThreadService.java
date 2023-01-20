package com.example.forum.services;

import com.example.forum.models.Groups;
import com.example.forum.models.Themes;
import com.example.forum.models.Threads;
import com.example.forum.repos.ThreadRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThreadService {

    ThreadRepo threadRepo;

    public ThreadService(ThreadRepo threadRepo) {
        this.threadRepo = threadRepo;
    }

    public void save(Threads threads){
        if(threads.getTitle() != null){
            this.threadRepo.save(threads);
        }
    }

    public Threads findThreadById(Long threadId) {
        Optional<Threads> thread = threadRepo.findById(threadId);
        return thread.orElse(new Threads());
    }

    public List<Threads> allThreads() {
        return threadRepo.findAll();
    }

    public Page<Threads> allThreadsOrderBy(Groups groups, Pageable pageable) {
        return threadRepo.findByGroupsOrderByIdDesc(groups, pageable); // выводит сначала новые записи
    }

    public long getUserPostsCount(Long userId){
        return threadRepo.countFirstBy(userId);
    }
}
