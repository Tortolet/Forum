package com.example.forum.controllers;

import com.example.forum.models.Roles;
import com.example.forum.models.Threads;
import com.example.forum.models.Users;
import com.example.forum.repos.ThreadRepo;
import com.example.forum.repos.UserRepo;
import com.example.forum.services.CommentService;
import com.example.forum.services.ThreadService;
import com.example.forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ThreadRepo threadRepo;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/registration")
    public String registration(Model model){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(Users user, Model model){

        if (!user.getPassword().equals(user.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }

        if(!userService.registrationUser(user)){
            model.addAttribute("messageError", "Пользователь уже существует");
            return "registration";
        }
//
        userService.save(user);

        return "redirect:/login";
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("!hasAuthority('ROLE_BANNED')")
    public String getUser(Model model, @PathVariable String username){
        Users user = userRepo.findByUsername(username);
        if(user != null) {
            model.addAttribute("user", user);

            List<Threads> posts = threadRepo.findByUsers(user);
            model.addAttribute("userPosts", posts);

            long countPosts;
            long countComments;

            countPosts = threadService.getUserPostsCount(user.getId());
            model.addAttribute("userCountPosts", countPosts);

            countComments = commentService.getUserCommentsCount(user.getId());
            model.addAttribute("userCountComments", countComments);

            return "user";
        }
        model.addAttribute("errorUser404", "Пользователя не существует");
        return "error";
    }

    @PostMapping("/updateUsername")
    @PreAuthorize("!hasAuthority('ROLE_BANNED')")
    public String updateInfoUser(@RequestParam Long id, @RequestParam String username, Model model){
        Users user = userService.findUserById(id);
        Users userInBD = userRepo.findByUsername(username);
        if(userInBD == null) {
            user.setUsername(username);
            userRepo.save(user);
            return "redirect:/logout";
        }
        model.addAttribute("errorUserExist", "Пользователь с таким именем уже существует");
        return "error";
    }

    @PostMapping("/updateUsernameForAdmin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateUsernameForAdmin(@RequestParam Long id, @RequestParam String username, Model model){
        Users user = userService.findUserById(id);
        Users userInBD = userRepo.findByUsername(username);
        if(userInBD == null) {
            user.setUsername(username);
            userRepo.save(user);
            return "redirect:/user/" + user.getUsername();
        }
        model.addAttribute("errorUserExist", "Пользователь с таким именем уже существует");
        return "error";
    }

    @PostMapping("/updatePassword")
    @PreAuthorize("!hasAuthority('ROLE_BANNED')")
    public String updatePassword(@RequestParam Long id, @RequestParam String password){
        Users user = userService.findUserById(id);
        user.setPassword(password);
        userService.save(user);
        return "redirect:/user/" + user.getUsername();
    }

    @PostMapping("/disableUser")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String disableCurrentUser(@RequestParam Long userID){
        Users user = userService.findUserById(userID);
        userService.disableUser(user);

        return "redirect:/user/" + user.getUsername();
    }

    @PostMapping("/activeUser")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String activateCurrentUser(@RequestParam Long userID){
        Users user = userService.findUserById(userID);
        userService.activeUser(user);

        return "redirect:/user/" + user.getUsername();
    }

    @PostMapping("/banUser")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String banCurrentUser(@RequestParam Long userID){
        Users user = userService.findUserById(userID);
        userService.banUser(user);

        return "redirect:/user/" + user.getUsername();
    }

    @PostMapping("/unbanUser")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String unbanCurrentUser(@RequestParam Long userID){
        Users user = userService.findUserById(userID);
        userService.unbanUser(user);

        return "redirect:/user/" + user.getUsername();
    }

    @GetMapping("/ban")
    public String banPage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepo.findByUsername(auth.getName());
        boolean banned = user.getRoles().contains(Roles.ROLE_BANNED);
        model.addAttribute("ban", banned);
        return "ban";
    }
}
