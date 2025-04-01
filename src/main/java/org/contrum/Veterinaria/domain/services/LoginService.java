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

    public User login(User user) throws Exception {
        User userValidate = userPort.findByUserName(user);
        if (user == null) {
            System.out.println("Null user");
            throw new Exception("Usuario o contraseña invalido");
        }

        if (!user.getPassword().equals(userValidate.getPassword())) {
            System.out.println("Invalid password! correct password is: " + user.getPassword());
            throw new Exception("Usuario o contraseña invalido");
        }

        currentLoggedUser = userValidate;
        return userValidate;
    }
}
