package com.example.forum.controllers;

import com.example.forum.models.Groups;
import com.example.forum.models.Themes;
import com.example.forum.models.Threads;
import com.example.forum.services.GroupService;
import com.example.forum.services.ThemeService;
import com.example.forum.services.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("!hasAuthority('ROLE_BANNED')")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private ThemeService themeService;

    @Autowired
    private ThreadService threadService;

    @PostMapping("/addGroup")
    public String addGroup(@RequestParam String groupName, @RequestParam Long themesId){
        Groups groups = new Groups();
        groups.setName(groupName);
        Themes themes = themeService.findThemeById(themesId);
        groups.setThemes(themes);
        groupService.save(groups);
        return "redirect:/";
    }

    @GetMapping("/group")
    public String getGroup(Model model, @RequestParam Long id, @PageableDefault(size = 5) Pageable pageable){
        Groups groups = groupService.findGroupById(id);
        Page<Threads> page = threadService.allThreadsOrderBy(groups, pageable);
        model.addAttribute("groupContent", groups.getName());
        model.addAttribute("getId", groups.getId());
        model.addAttribute("threads", page);
        model.addAttribute("group", groups);
        return "groups";
    }
}
