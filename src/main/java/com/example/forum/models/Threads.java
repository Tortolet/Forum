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

    public Threads() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Groups getGroups() {
        return groups;
    }

    public void setGroups(Groups groups) {
        this.groups = groups;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Date getDateChange() {
        return dateChange;
    }

    public void setDateChange(Date dateChange) {
        this.dateChange = dateChange;
    }

    public boolean isPin() {
        return pin;
    }

    public void setPin(boolean pin) {
        this.pin = pin;
    }

    public Set<Users> getUsersCom() {
        return usersCom;
    }

    public void setUsersCom(Set<Users> usersCom) {
        this.usersCom = usersCom;
    }
}
