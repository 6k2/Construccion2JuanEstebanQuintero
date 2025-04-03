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

    /**
     * Show the administrator menu.
     * <p>
     * This method displays the administrator menu, which allows the user to
     * create a new seller, create a new veterinarian, list all sellers, list all
     * veterinarians, or to exit the application.
     * <p>
     * If the user chooses to create a new seller or a new veterinarian, the
     * corresponding method is called and the user is asked for the required
     * information. If the user chooses to list all sellers or all veterinarians,
     * the list is displayed using the {@link ConsolePaginator} utility.
     * <p>
     * If the user chooses to exit the application, the method simply returns.
     * <p>
     * If an exception occurs while executing any of the methods, the error
     * message is printed and the menu is displayed again.
     */
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
                "",
                "X. Cerrar sesión"
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
            Printer.print(e.getMessage());
        }
        menu();
    }

        /**
         * Registers a new seller with the given name, document, age, username, password, and role.
         * @throws Exception if a person with the same document or a user with the same
         * username already exists.
         */
    private void createSeller() throws Exception {
        Printer.print("Ingrese el nombre del vendedor");
        String name = personValidator.nameValidator(Printer.read());
        Printer.print("Ingrese la cedula del vendedor");
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

    /**
     * Registers a new veterinarian with the given details.
     *
     * This method prompts the user to input the veterinarian's name, document ID,
     * age, username, and password. It validates the input data and creates a new
     * Veterinarian object with the provided information. The veterinarian is then
     * registered using the veterinarian service.
     *
     * @throws Exception if a person with the same document or a user with the same
     *                   username already exists.
     */
    private void createVeterinarian() throws Exception {
        Printer.print("Ingrese el nombre del veterinario");
        String name = personValidator.nameValidator(Printer.read());
        Printer.print("Ingrese la cedula del veterinario");
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