package org.contrum.Veterinaria.domain.services;

import lombok.Getter;
import org.contrum.Veterinaria.domain.models.User;
import org.contrum.Veterinaria.ports.UserPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserPort userPort;

    @Getter
    private User currentLoggedUser = null;

    /**
     * Logs in a user by validating the provided username and password.
     *
     * @param user The user object containing the username and password to validate.
     * @return The validated User object if the login is successful.
     * @throws Exception If the username or password is invalid.
     */
    public User login(User user) throws Exception {
        User userValidate = userPort.findByUserName(user);
        if (userValidate == null) {
            throw new Exception("Usuario o contraseña invalido");
        }

        if (!user.getPassword().equals(userValidate.getPassword())) {
            throw new Exception("Usuario o contraseña invalido");
        }

        currentLoggedUser = userValidate;
        return userValidate;
    }
}
