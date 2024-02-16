package org.example.splitwise.entities;

import jakarta.persistence.*;

public class Friends {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;


    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User friend;

}
