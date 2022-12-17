package com.example.forum.services;

import com.example.forum.models.Themes;
import com.example.forum.repos.ThemeRepo;
import org.springframework.stereotype.Service;
import org.springframework.ui.context.Theme;

import java.util.List;
import java.util.Optional;

@Service
public class ThemeService {

    ThemeRepo themeRepo;

    public ThemeService(ThemeRepo themeRepo) {
        this.themeRepo = themeRepo;
    }

    public void save(Themes themes){
        if(themes.getName() != null){
            this.themeRepo.save(themes);
        }
    }

    public Themes findThemeById(Long themeId) {
        Optional<Themes> theme = themeRepo.findById(themeId);
        return theme.orElse(new Themes());
    }



    public List<Themes> allThemes() {
        return themeRepo.findAll();
    }
}
