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
public class AdminInput implements InputPort {
    @Autowired
    private PersonValidator personValidator;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private AdminService service;

    public void menu() {

        Printer.print(
                "<center>Elige una opción",
                "1. Crear vendedor",
                "2. Crear medico veterinario",
                "3. Listar vendedores"
        );

        String choice = Printer.read();
        try {
            switch (choice) {
                case "1": {
                    this.createSeller();

                    Printer.print("\nVendedor registrado con exito!");
                    break;
                }
                case "2": {
                    this.createVeterinarian();

                    Printer.print("\nMedico veterinario registrado con exito!");
                    break;
                }
                case "3": {
                    Printer.print("Listado de vendedores");
                    service.getSellers().forEach(seller -> {
                        Printer.print(seller.getDocument() + " - " + seller.getName() + " role: " + seller.getRole().name());
                    });
                    break;
                }
                default:
                    System.out.println("opcion no valida");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Printer.print(e.getMessage());
        }
        menu();
    }

    private void createSeller() throws Exception {
        Printer.print("Ingrese el nombre del vendedor");
        String name = personValidator.nameValidator(Printer.read());
        Printer.print("Ingrese el documento del vendedor");
        long document = personValidator.documentValidator(Printer.read());
        Printer.print("Ingrese la edad del vendedor");
        int age = personValidator.ageValidator(Printer.read());
        Printer.print("Ingrese el userName del vendedor");
        String userName = userValidator.userNameValidator(Printer.read());
        Printer.print("Ingrese la contraseña del venedor");
        String password = userValidator.passwordValidator(Printer.read());

        Seller seller = new Seller();
        seller.setName(name);
        seller.setDocument(document);
        seller.setAge(age);
        seller.setUserName(userName);
        seller.setPassword(password);
        seller.setRole(Person.Role.SELLER);

        seller.setUserName(userName);
        seller.setPassword(password);

        service.registerSeller(seller);
    }

    private void createVeterinarian() throws Exception {
        Printer.print("Ingrese el nombre del veterinario");
        String name = personValidator.nameValidator(Printer.read());
        Printer.print("Ingrese el documento del veterinario");
        long document = personValidator.documentValidator(Printer.read());
        Printer.print("Ingrese la edad del veterinario");
        int age = personValidator.ageValidator(Printer.read());
        Printer.print("Ingrese el userName del veterinario");
        String userName = userValidator.userNameValidator(Printer.read());
        Printer.print("Ingrese la contraseña del veterinario");
        String password = userValidator.passwordValidator(Printer.read());

        Veterinarian veterinarian = new Veterinarian();

        veterinarian.setName(name);
        veterinarian.setDocument(document);
        veterinarian.setAge(age);
        veterinarian.setUserName(userName);
        veterinarian.setPassword(password);
        veterinarian.setRole(Person.Role.VETERINARIAN);

        service.registerVeterinarian(veterinarian);
    }
}