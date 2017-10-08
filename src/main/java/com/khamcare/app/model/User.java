package com.khamcare.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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

    @JsonIgnore
    @Column(name="password", nullable = false)
    String password;

    @JsonIgnore
    @Transient
    String passwordConfirmation;

    @Column(name="avatar_url")
    String avatarUrl;
}
