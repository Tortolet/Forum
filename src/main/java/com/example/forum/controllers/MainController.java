package com.example.forum.controllers;

import com.example.forum.models.Users;
import com.example.forum.services.GroupService;
import com.example.forum.services.ThemeService;
import com.example.forum.services.ThreadService;
import com.example.forum.services.UserService;
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

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("allThemes", themeService.allThemes());
        model.addAttribute("allGroups", groupService.allThemes());
        if(threadService.allThreads().size() - 1 > 0)
            model.addAttribute("lastThread", threadService.allThreads().get(threadService.allThreads().size() - 1)); // Last thread

        if(threadService.allThreads().size() - 1 > 0) { // Расчет у кого больше всего постов и имя пользователя с большим количеством постов
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

        return "index";
    }
}
