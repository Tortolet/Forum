package com.example.forum.controllers;

import com.example.forum.services.GroupService;
import com.example.forum.services.ThemeService;
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

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("allThemes", themeService.allThemes());
        model.addAttribute("allGroups", groupService.allThemes());
        return "index";
    }
}
