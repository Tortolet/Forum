package com.example.forum.services;

import com.example.forum.models.Roles;
import com.example.forum.models.Users;
import com.example.forum.repos.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserRepo userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepo userRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepo;
        this.bCryptPasswordEncoder = passwordEncoder;
    }

    public Users findUserById(Long userId) {
        Optional<Users> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new Users());
    }

    public List<Users> allUsers() {
        return userRepository.findAll();
    }

    public void save(Users user){
        String enPas = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(enPas);
        this.userRepository.save(user);
    }

    public boolean registrationUser(Users user){
        Users userInBD = userRepository.findByUsername(user.getUsername());

        if(userInBD != null){
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Roles.ROLE_USER));
        return true;
    }

}