package com.example.forum.controllers;

import com.example.forum.models.Groups;
import com.example.forum.models.Threads;
import com.example.forum.models.Users;
import com.example.forum.repos.UserRepo;
import com.example.forum.services.GroupService;
import com.example.forum.services.ThreadService;
import com.example.forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ThreadController {

    @Autowired
    private ThreadService threadService;

    @Autowired
    private GroupService groupService;

    UserRepo userRepo;

//    @GetMapping("/group/add_post")
//    private String getAddPost(Model model, @RequestParam Long id, @RequestParam String title, @RequestParam String desc){
////        Users userInDB = userRepo.findByUsername(user.getUsername());
////        Groups groupInDB = groupService.findGroupById(id);
////        Threads threads = new Threads();
////
////        threads.setGroups(groupInDB);
////        threads.setUsers(userInDB);
////
////        threads.setPin(false);
////
////        threads.setTitle(title);
////        threads.setContent(desc);
////
////        threadService.save(threads);
////
////        return "add_post";
//    }
}
