package com.example.forum.models;


import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Threads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Groups groups;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;

    private String title;

    private String content;

    private Date dateCreated = new Date(System.currentTimeMillis());

    private Date dateChange = new Date(System.currentTimeMillis());

    private boolean pin;

    @ManyToMany(mappedBy = "threads")
    private Set<Users> usersCom = new HashSet<>();

}
