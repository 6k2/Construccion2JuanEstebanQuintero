package org.contrum.Veterinaria.adapters.inputs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.contrum.Veterinaria.domain.models.Person;
import org.contrum.Veterinaria.domain.models.Seller;
import org.contrum.Veterinaria.domain.models.Veterinarian;
import org.contrum.Veterinaria.domain.services.AdminService;
import org.contrum.Veterinaria.ports.InputPort;
import org.contrum.Veterinaria.utils.Printer;
import org.contrum.Veterinaria.utils.validators.PersonValidator;
import org.contrum.Veterinaria.utils.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Setter
@Getter
@NoArgsConstructor
@Component
public class VeterinarianInput implements InputPort {
    @Autowired
    private PersonValidator personValidator;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private AdminService service;

    public void menu() {

        // TODO: idk
    }
}