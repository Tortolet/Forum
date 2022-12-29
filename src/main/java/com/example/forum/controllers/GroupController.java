package com.example.forum.controllers;

import com.example.forum.models.Groups;
import com.example.forum.models.Themes;
import com.example.forum.repos.GroupRepo;
import com.example.forum.services.GroupService;
import com.example.forum.services.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private ThemeService themeService;

    @PostMapping("/addGroup")
    private String addGroup(@RequestParam String groupName, @RequestParam Long themesId){
        Groups groups = new Groups();
        groups.setName(groupName);
        Themes themes = themeService.findThemeById(themesId);
        groups.setThemes(themes);
        groupService.save(groups);
        return "redirect:/";
    }

    @GetMapping("/group")
    private String getGroup(Model model, @RequestParam Long id){
        Groups groups = groupService.findGroupById(id);
        model.addAttribute("groupContent", groups.getName());
        return "groups";
    }
}
