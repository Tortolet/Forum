package com.example.forum.services;

import com.example.forum.models.Groups;
import com.example.forum.models.Threads;
import com.example.forum.repos.ThreadRepo;
import org.springframework.stereotype.Service;

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
}
