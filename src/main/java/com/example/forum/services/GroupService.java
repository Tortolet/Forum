package com.example.forum.services;

import com.example.forum.models.Groups;
import com.example.forum.repos.GroupRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Groups> allThemes() {
        return groupRepo.findAll();
    }

    public Groups findGroupById(Long groupId) {
        Optional<Groups> groups = groupRepo.findById(groupId);
        return groups.orElse(new Groups());
    }
}
