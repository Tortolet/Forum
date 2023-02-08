package com.example.forum.controllers;

import com.example.forum.models.Themes;
import com.example.forum.services.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("!hasAuthority('ROLE_BANNED')")
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @PostMapping("/addTheme")
    public String addTheme(Model model, @RequestParam String name){
        Themes themes = new Themes();
        themes.setName(name);
        themeService.save(themes);
        return "redirect:/";
    }
}
