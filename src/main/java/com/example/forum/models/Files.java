package com.example.forum.models;

import javax.persistence.*;

@Entity
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    @ManyToOne (fetch = FetchType.LAZY)
    private Threads threads;
}
