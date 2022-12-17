package com.example.forum.services;

import com.example.forum.models.Groups;
import com.example.forum.models.Themes;
import com.example.forum.repos.GroupRepo;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    GroupRepo groupRepo;

    public GroupService(GroupRepo groupRepo) {
        this.groupRepo = groupRepo;
    }

    public void save(Groups groups){
        if(groups.getName() != null){
            this.groupRepo.save(groups);
        }
    }
}
