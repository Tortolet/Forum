package com.example.forum.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Threads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Добавить username

    private String title;

    private String content;

    private Date dateCreated = new Date(System.currentTimeMillis());

    private Date dateChange = new Date(System.currentTimeMillis());

    private boolean pin;

}
