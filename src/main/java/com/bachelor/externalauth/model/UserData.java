package com.bachelor.externalauth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@Getter
@Document(collection = "user")
@ToString
@NoArgsConstructor
@Setter
public class UserData {
    @Id
    private String id;
    private String username;
    private String password;
    @Field
    private String[] roles;
}
