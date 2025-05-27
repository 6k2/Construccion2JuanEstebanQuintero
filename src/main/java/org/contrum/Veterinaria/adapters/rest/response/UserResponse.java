package org.contrum.Veterinaria.adapters.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.contrum.Veterinaria.domain.models.User;

@Getter @Setter @NoArgsConstructor
public class UserResponse extends PersonResponse {
    private String userName;

    public UserResponse(User user) {
        super(user);
    }
}
