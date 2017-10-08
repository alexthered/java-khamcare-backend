package com.khamcare.app.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    Long id;

    @Column(name="first_name", nullable = false)
    String firstName;

    @Column(name="last_name", nullable = false)
    String lastName;

    @Column(name="email", nullable = false)
    String email;

    @Column(name="password", nullable = false)
    String password;

    @Transient
    String passwordConfirmation;
}
