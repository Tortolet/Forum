package com.example.forum.controllers;

import com.example.forum.services.GroupService;
import com.example.forum.services.ThemeService;
import com.example.forum.services.ThreadService;
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

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("allThemes", themeService.allThemes());
        model.addAttribute("allGroups", groupService.allThemes());
        if(threadService.allThreads().size() - 1 > 0)
            model.addAttribute("lastThread", threadService.allThreads().get(threadService.allThreads().size() - 1)); // Last thread
        return "index";
    }
}
