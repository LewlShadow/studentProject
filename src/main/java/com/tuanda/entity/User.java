package com.tuanda.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email_address")
    private String email;

    @Column(name = "name")
    private String username;

    @Column
    @JsonIgnore
    private String password;

    private String role;

}
