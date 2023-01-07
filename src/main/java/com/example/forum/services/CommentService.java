package com.example.forum.services;

import com.example.forum.models.Comments;
import com.example.forum.repos.CommentRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    CommentRepo commentRepo;

    public CommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public void save(Comments comments){
        if(comments.getContent() != null){
            this.commentRepo.save(comments);
        }
    }

    public List<Comments> allThreadsOrderByIdThread(long id) {
        return commentRepo.findByThreads_Id(id); // выводит сначала новые записи
    }
}
