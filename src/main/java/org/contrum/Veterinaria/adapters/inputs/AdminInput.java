package org.contrum.Veterinaria.adapters.inputs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.contrum.Veterinaria.adapters.entities.seller.repository.SellerRepository;
import org.contrum.Veterinaria.adapters.entities.veterinarian.repository.VeterinarianRepository;
import org.contrum.Veterinaria.domain.models.Person;
import org.contrum.Veterinaria.domain.models.Seller;
import org.contrum.Veterinaria.domain.models.Veterinarian;
import org.contrum.Veterinaria.domain.services.AdminService;
import org.contrum.Veterinaria.ports.InputPort;
import org.contrum.Veterinaria.utils.ConsolePaginator;
import org.contrum.Veterinaria.utils.Printer;
import org.contrum.Veterinaria.utils.validators.PersonValidator;
import org.contrum.Veterinaria.utils.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private VeterinarianRepository veterinarianRepository;

    @Autowired
    private ConsolePaginator consolePaginator;

    public void menu() {
        Printer.print(
                "",
                "Menu Administrador",
                "Elige una opción",
                "",
                "1. Crear vendedor",
                "2. Crear medico veterinario",
                "3. Listar vendedores",
                "4. Listar medicos veterinarios",
                "X. Volver"
        );

        String choice = Printer.read().toLowerCase();
        try {
            switch (choice) {
                case "1": {
                    this.createSeller();
                    Printer.print("\nVendedor registrado con éxito!");
                    break;
                }
                case "2": {
                    this.createVeterinarian();
                    Printer.print("\nMédico veterinario registrado con éxito!");
                    break;
                }
                case "3": {
                    consolePaginator.paginate(
                            sellerRepository,
                            seller -> List.of(
                                    "<border>",
                                    " Usuario: " + seller.getUser().getUserName() + " (SellerId: "+seller.getSellerId()+")",
                                    " Nombre: " + seller.getUser().getPerson().getName(),
                                    " Cédula: " + seller.getUser().getPerson().getDocument(),
                                    " Edad: " + seller.getUser().getPerson().getAge(),
                                    "<border>"
                            ),
                            "Listado de vendedores",
                            10
                    );
                    break;
                }
                case "4": {
                    consolePaginator.paginate(
                            veterinarianRepository,
                            vet -> List.of(
                                    "<border>",
                                    " Usuario: " + vet.getUser().getUserName() + " (SellerId: "+vet.getVeterinarianId()+")",
                                    " Nombre: " + vet.getUser().getPerson().getName(),
                                    " Cédula: " + vet.getUser().getPerson().getDocument(),
                                    " Edad: " + vet.getUser().getPerson().getAge(),
                                    "<border>"
                            ),
                            "Listado de veterinarios",
                            10
                    );
                    break;
                }
                case "x": {
                    return;
                }
                default:
                    System.out.println("Opción no válida");
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