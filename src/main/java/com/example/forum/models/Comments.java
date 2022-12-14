package com.example.forum.models;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Threads threads;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;

    private String content;

    private Date dateCreated = new Date(System.currentTimeMillis());

    @ManyToMany(mappedBy = "comments")
    private Set<Users> usersCom = new HashSet<>();
}
