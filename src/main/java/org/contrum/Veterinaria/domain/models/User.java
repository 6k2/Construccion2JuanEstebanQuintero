package org.contrum.Veterinaria.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class User extends Person {
    private long userId;
    private String userName;
    private String password;
}