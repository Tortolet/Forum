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

    public Comments() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Threads getThreads() {
        return threads;
    }

    public void setThreads(Threads threads) {
        this.threads = threads;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Set<Users> getUsersCom() {
        return usersCom;
    }

    public void setUsersCom(Set<Users> usersCom) {
        this.usersCom = usersCom;
    }
}
