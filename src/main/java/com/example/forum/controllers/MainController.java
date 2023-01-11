package com.example.forum.controllers;

import com.example.forum.models.Users;
import com.example.forum.repos.UserRepo;
import com.example.forum.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private ThemeService themeService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("allThemes", themeService.allThemes());
        model.addAttribute("allGroups", groupService.allThemes());
        if(threadService.allThreads().size() > 0)
            model.addAttribute("lastThread", threadService.allThreads().get(threadService.allThreads().size() - 1)); // Last thread

        if(threadService.allThreads().size() > 0) { // Расчет у кого больше всего постов и имя пользователя с большим количеством постов
            int max = Integer.MIN_VALUE;
            long local = 0;
            for(long i = 1; i <= userService.allUsers().size(); i++){
                max = (int) Math.max(max, threadService.getUserPostsCount(i));

                if(max == threadService.getUserPostsCount(i)) {
                    local = i;
                }
            }
            Users usersMaxPosts = userService.findUserById(local);

            model.addAttribute("maxPosts", max);
            model.addAttribute("nameOfMaxPostsUser", usersMaxPosts.getUsername());
        }

        if(commentService.allComments().size() > 0){
            int com_max = Integer.MIN_VALUE;
            long com_local = 0;
            for (long i = 0; i <= userService.allUsers().size(); i++) {
                com_max = (int) Math.max(com_max, commentService.getUserCommentsCount(i));

                if(com_max == commentService.getUserCommentsCount(i)){
                    com_local = i;
                }
            }
            Users usersMaxComments = userService.findUserById(com_local);

            model.addAttribute("maxComments", com_max);
            model.addAttribute("nameOfMaxCommentsUser", usersMaxComments.getUsername());
        }

        if(userRepo.showMaxLikePost() != null)
            model.addAttribute("maxLikePost", threadService.findThreadById(Long.valueOf(userRepo.showMaxLikePost())));
        return "index";
    }
}
