package com.example.forum.services;

import com.example.forum.models.Files;
import com.example.forum.repos.FileRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilesService {

    FileRepo fileRepo;

    public FilesService(FileRepo fileRepo) {
        this.fileRepo = fileRepo;
    }

    public List<Files> allFiles() {
        return fileRepo.findAll();
    }
}
