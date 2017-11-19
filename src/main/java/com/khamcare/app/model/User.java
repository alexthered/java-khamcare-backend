package com.khamcare.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Document(collection = "users")
public class User {

    @Id
    String id;

    String firstName;

    String lastName;

    @Indexed(unique = true)
    String email;

    @JsonIgnore
    String password;

    @JsonIgnore
    @Transient
    String passwordConfirmation;

    String avatarUrl;
}
