package com.example.forum.controllers;

import com.example.forum.models.Groups;
import com.example.forum.models.Themes;
import com.example.forum.repos.GroupRepo;
import com.example.forum.services.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GroupController {

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private ThemeService themeService;

    @PostMapping("/addGroup")
    private String addGroup(@RequestParam String groupName, @RequestParam Long themesId){
        Groups groups = new Groups();
        groups.setName(groupName);
        Themes themes = themeService.findThemeById(themesId);
        groups.setThemes(themes);
        groupRepo.save(groups);
        return "redirect:/";
    }
}
