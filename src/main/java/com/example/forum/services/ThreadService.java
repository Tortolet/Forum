package com.example.forum.services;

import com.example.forum.models.Groups;
import com.example.forum.models.Themes;
import com.example.forum.models.Threads;
import com.example.forum.repos.ThreadRepo;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Threads> allThreads() {
        return threadRepo.findAll();
    }
}
