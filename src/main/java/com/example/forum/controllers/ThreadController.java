package com.example.forum.controllers;

import com.example.forum.models.Comments;
import com.example.forum.models.Groups;
import com.example.forum.models.Threads;
import com.example.forum.models.Users;
import com.example.forum.repos.UserRepo;
import com.example.forum.services.CommentService;
import com.example.forum.services.GroupService;
import com.example.forum.services.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ThreadController {

    @Autowired
    private ThreadService threadService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CommentService commentService;

    @GetMapping("/group/add_post")
    public String getPost(Model model, @RequestParam Long id){
        Groups groups = groupService.findGroupById(id);
        model.addAttribute("getId", groups.getId());
        return "add_post";
    }

    @PostMapping("addPost")
    public String postPost(@RequestParam Long id, @RequestParam String title, @RequestParam String content, @RequestParam String username){
        Users userInDB = userRepo.findByUsername(username);
        Groups groupInDB = groupService.findGroupById(id);
        Threads threads = new Threads();

        threads.setGroups(groupInDB);
        threads.setUsers(userInDB);

        threads.setPin(false);

        threads.setTitle(title);
        threads.setContent(content);

        threadService.save(threads);

//        return "redirect:/group?id=" + id;

        //noinspection SpringMVCViewInspection
        return "redirect:/group/" + id + "/post?id=" + threads.getId();
    }

    @GetMapping("/group/{groupId}/post")
    public String showPost(Model model, @PathVariable Long groupId, @RequestParam Long id){
        Threads post = threadService.findThreadById(id);
        model.addAttribute("thread", post);
        model.addAttribute("comments", commentService.allThreadsOrderByIdThread(id));
        return "post";
    }

    @PostMapping("/pin")
    public String pinPost(@RequestParam Long id){
        Threads threads = threadService.findThreadById(id);
        long groupId = threads.getGroups().getId();
        threads.setPin(true);
        threadService.save(threads);
        return "redirect:/group?id=" + groupId;
    }

    @PostMapping("/unpin")
    public String unpinPost(@RequestParam Long id){
        Threads threads = threadService.findThreadById(id);
        long groupId = threads.getGroups().getId();
        threads.setPin(false);
        threadService.save(threads);
        return "redirect:/group?id=" + groupId;
    }

    @PostMapping("comment")
    public String addComment(@RequestParam Long threadId, @RequestParam String username, @RequestParam String content){
        Threads threadInDB = threadService.findThreadById(threadId);
        Users userInDB = userRepo.findByUsername(username);
        Comments comments = new Comments();

        comments.setThreads(threadInDB);
        comments.setUsers(userInDB);
        comments.setContent(content);

        commentService.save(comments);

        return "redirect:/group/" + threadInDB.getGroups().getId() + "/post?id=" + threadInDB.getId();
    }
}
