package com.example.forum.controllers;

import com.example.forum.models.Comments;
import com.example.forum.models.Groups;
import com.example.forum.models.Threads;
import com.example.forum.models.Users;
import com.example.forum.repos.FileRepo;
import com.example.forum.repos.UserRepo;
import com.example.forum.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
public class ThreadController {

    public static String uploadDir = System.getProperty("user.dir") + "/upload";

    @Autowired
    private ThreadService threadService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FileRepo fileRepo;

    @Autowired
    private FilesService filesService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @GetMapping("/group/add_post")
    public String getPost(Model model, @RequestParam Long id){
        Groups groups = groupService.findGroupById(id);
        model.addAttribute("getId", groups.getId());
        return "add_post";
    }

    @PostMapping("addPost")
    public String postPost(@RequestParam Long id, @RequestParam String title, @RequestParam String content, @RequestParam String username, @RequestParam MultipartFile[] files){
        Users userInDB = userRepo.findByUsername(username);
        Groups groupInDB = groupService.findGroupById(id);
        Threads threads = new Threads();

        threads.setGroups(groupInDB);
        threads.setUsers(userInDB);

        threads.setPin(false);

        threads.setTitle(title);
        threads.setContent(content);

        threadService.save(threads);

//        StringBuilder fileNames = new StringBuilder();
        for (MultipartFile file: files) {
            com.example.forum.models.Files files1 = new com.example.forum.models.Files();
            Path fileNameAndPath = Paths.get(uploadDir, file.getOriginalFilename());
//            fileNames.append(file.getOriginalFilename());
            files1.setFilename(file.getOriginalFilename());
            files1.setThreads(threads);
            try {
                if(!Objects.requireNonNull(file.getOriginalFilename()).isEmpty())
                    Files.write(fileNameAndPath, file.getBytes());
                if(!files1.getFilename().isEmpty())
                    fileRepo.save(files1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        return "redirect:/group?id=" + id;

        //noinspection SpringMVCViewInspection
        return "redirect:/group/" + id + "/post?id=" + threads.getId();
    }

    @GetMapping("/group/{groupId}/post")
    public String showPost(Model model, @PathVariable Long groupId, @RequestParam Long id){
        Threads post = threadService.findThreadById(id);
        Set<Users> usersSet = new HashSet<>();
        model.addAttribute("thread", post);
        model.addAttribute("comments", commentService.allThreadsOrderByIdThread(id));
        model.addAttribute("files", filesService.allFiles());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepo.findByUsername(auth.getName());
        usersSet.add(user);
        model.addAttribute("userSet", usersSet);

        model.addAttribute("countLikes", userRepo.countByThreads(post));

        Map<Comments, Long> test = new HashMap<>();
        Map<Comments, String> dateComment = new HashMap<>();
        if(commentService.allThreadsOrderByIdThread(id).size() > 0){
            long temp;
            for(int i = 0; i < commentService.allThreadsOrderByIdThread(id).size(); i++){
                temp = userRepo.countByComments(commentService.allThreadsOrderByIdThread(id).get(i));
                test.put(commentService.allThreadsOrderByIdThread(id).get(i), temp);

                String dateCommentString = String.valueOf(commentService.allThreadsOrderByIdThread(id).get(i).getDateCreated());
                StringBuilder dateBuilder = new StringBuilder(dateCommentString);
                String dayComment = dateBuilder.substring(8,10);
                String monthComment = dateBuilder.substring(5,7);
                String yearComment = dateBuilder.substring(0,4);
                String timeComment = dateBuilder.substring(11,19);

                String resultDateComment = dayComment + "-" + monthComment + "-" + yearComment + " " + timeComment;
                dateComment.put(commentService.allThreadsOrderByIdThread(id).get(i), resultDateComment);
            }
        }
        model.addAttribute("test", test);
        model.addAttribute("dateComment", dateComment);

        String dateCreated = String.valueOf(post.getDateCreated());
        StringBuilder builder = new StringBuilder(dateCreated);
        String day = builder.substring(8,10);
        String month = builder.substring(5,7);
        String year = builder.substring(0,4);
        String time = builder.substring(11,19);
        String resultDateCreated = day + "-" + month + "-" + year + " " + time;
        model.addAttribute("dateCreated", resultDateCreated);

        String dateChange = String.valueOf(post.getDateChange());
        StringBuilder builderChange = new StringBuilder(dateChange);
        String dayChange = builderChange.substring(8,10);
        String monthChange = builderChange.substring(5,7);
        String yearChange = builderChange.substring(0,4);
        String timeChange = builderChange.substring(11,19);
        String resultDateChange = dayChange + "-" + monthChange + "-" + yearChange + " " + timeChange;
        model.addAttribute("dateChange", resultDateChange);

        return "post";
    }

    @PostMapping("/pin")
    public String pinPost(@RequestParam Long id){
        Threads threads = threadService.findThreadById(id);
        long groupId = threads.getGroups().getId();
        threads.setPin(true);
        threadService.save(threads);
        return "redirect:/group?id=" + groupId;
    }

    @PostMapping("/unpin")
    public String unpinPost(@RequestParam Long id){
        Threads threads = threadService.findThreadById(id);
        long groupId = threads.getGroups().getId();
        threads.setPin(false);
        threadService.save(threads);
        return "redirect:/group?id=" + groupId;
    }

    @PostMapping("comment")
    public String addComment(@RequestParam Long threadId, @RequestParam String username, @RequestParam String content){
        Threads threadInDB = threadService.findThreadById(threadId);
        Users userInDB = userRepo.findByUsername(username);
        Comments comments = new Comments();

        comments.setThreads(threadInDB);
        comments.setUsers(userInDB);
        comments.setContent(content);

        commentService.save(comments);

        return "redirect:/group/" + threadInDB.getGroups().getId() + "/post?id=" + threadInDB.getId();
    }

    @PostMapping("/likePost")
    public String likePost(@RequestParam Long postId, @RequestParam String username) {
        Users user = userRepo.findByUsername(username);
        Threads post = threadService.findThreadById(postId);

        user.addThread(post);

        userRepo.save(user);

        return "redirect:/group/" + post.getGroups().getId() + "/post?id=" + post.getId();
    }

    @PostMapping("/dislikePost")
    public String dislikePost(@RequestParam Long postId, @RequestParam String username) {
        Users user = userRepo.findByUsername(username);
        Threads post = threadService.findThreadById(postId);

        user.removeThread(post);

        userRepo.save(user);

        return "redirect:/group/" + post.getGroups().getId() + "/post?id=" + post.getId();
    }

    @PostMapping("/likeComment")
    public String likeComment(@RequestParam Long postId, @RequestParam String username, @RequestParam Long commentId){
        Users user = userRepo.findByUsername(username);
        Threads post = threadService.findThreadById(postId);
        Comments comments = commentService.findCommentById(commentId);

        user.addComment(comments);

        userRepo.save(user);

        return "redirect:/group/" + post.getGroups().getId() + "/post?id=" + post.getId();
    }

    @PostMapping("/dislikeComment")
    public String dislikeComment(@RequestParam Long postId, @RequestParam String username, @RequestParam Long commentId){
        Users user = userRepo.findByUsername(username);
        Threads post = threadService.findThreadById(postId);
        Comments comments = commentService.findCommentById(commentId);

        user.removeComment(comments);

        userRepo.save(user);

        return "redirect:/group/" + post.getGroups().getId() + "/post?id=" + post.getId();
    }

    @PostMapping("/updatePost")
    public String updateString(@RequestParam Long postId, @RequestParam String title, @RequestParam String content, @RequestParam(defaultValue = "false") boolean pin){
        Threads post = threadService.findThreadById(postId);

        post.setTitle(title);
        post.setContent(content);
        post.setPin(pin);
        post.setDateChange(new Date(System.currentTimeMillis()));

        threadService.save(post);

        return "redirect:/group/" + post.getGroups().getId() + "/post?id=" + post.getId();
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.allUsers());
        Map<Users, Long> usersThreadsMap = new HashMap<>();
        Map<Users, Long> usersCommentsMap = new HashMap<>();

        if(threadService.allThreads().size() > 0){
            for (int i = 0; i < userService.allUsers().size(); i++) {
                usersThreadsMap.put(userService.allUsers().get(i), threadService.getUserPostsCount(userService.allUsers().get(i).getId()));
            }
            model.addAttribute("threads", usersThreadsMap);
        }

        if(commentService.allComments().size() > 0){
            for (int i = 0; i < userService.allUsers().size(); i++) {
                usersCommentsMap.put(userService.allUsers().get(i), commentService.getUserCommentsCount(userService.allUsers().get(i).getId()));
            }
            model.addAttribute("comments", usersCommentsMap);
        }

        return "users";
    }
}
