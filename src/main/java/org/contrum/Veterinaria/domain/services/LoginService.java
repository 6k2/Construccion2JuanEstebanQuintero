package org.contrum.Veterinaria.domain.services;

import org.contrum.Veterinaria.domain.models.User;
import org.contrum.Veterinaria.ports.UserPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserPort userPort;

    public User login(User user) throws Exception {
        User userValidate = userPort.findByUserName(user);
        if (user == null) {
            throw new Exception("Usuario o contraseña invalido");
        }

        if (user.getPassword().equals(userValidate.getPassword())) {
            throw new Exception("Usuario o contraseña invalido");
        }
        return userValidate;
    }
}
