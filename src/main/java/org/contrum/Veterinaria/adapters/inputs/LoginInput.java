package org.contrum.Veterinaria.adapters.inputs;

import org.contrum.Veterinaria.domain.models.Person;
import org.contrum.Veterinaria.domain.models.User;
import org.contrum.Veterinaria.domain.services.LoginService;
import org.contrum.Veterinaria.ports.InputPort;
import org.contrum.Veterinaria.utils.Printer;
import org.contrum.Veterinaria.utils.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoginInput implements InputPort {

    private final Map<Person.Role, InputPort> inputs = new HashMap<>();

    @Autowired
    private LoginService service;
    @Autowired
    private UserValidator userValidator;

    public LoginInput(AdminInput adminInput, VeterinarianInput veterinarianInput, SellerInput sellerInput) {
        super();
        inputs.put(Person.Role.ADMINISTRATOR, adminInput);
        inputs.put(Person.Role.VETERINARIAN, veterinarianInput);
        inputs.put(Person.Role.SELLER, sellerInput);
    }

    /**
     * Shows the main menu of the application to the user.
     *
     * The user can choose to login, login as an administrator, or exit the application.
     *
     * @throws Exception if any error occurs.
     */
    @Override
    public void menu() throws Exception {
        Printer.print("\n\n\n\n\n\n\n\n\n");
        Printer.print(
                "<center>Bienvenido a la veterinaria",
                "",
                "1. Iniciar sesión",
                "2. Ingresar como Administrador",
                "3. Salir"
        );

        String read = Printer.read();
        switch (read) {
            case "1":
                this.loginMenu();
                break;
            case "2":
                InputPort inputPort = inputs.get(Person.Role.ADMINISTRATOR);
                inputPort.menu();
                break;
            case "3":
                Printer.print("Hasta una próxima ocasión");
                return;
            default:
                Printer.print("Ha elegido una opción inválida!");
                break;
        }
        this.menu();
    }

    /**
     * Allows the user to login using their username and password.
     *
     * Prompts the user for their username and password, validates the input,
     * and logs in the user using the login service. If the login is successful,
     * the corresponding menu for the user's role is displayed. If the login
     * fails, the login menu is displayed again.
     *
     * @throws Exception if the login fails.
     */
    private void loginMenu() throws Exception {
        try {
            Printer.print("\nIngrese su usuario");
            String username = userValidator.userNameValidator(Printer.read());
            Printer.print("Ingrese su contraseña");
            String password = userValidator.passwordValidator(Printer.read());

            User loginUser = new User();
            loginUser.setUserName(username);
            loginUser.setPassword(password);

            User user = service.login(loginUser);

            InputPort inputPort = inputs.get(user.getRole());
            inputPort.menu();
        } catch (Exception e) {
            Printer.print(e.getMessage());
            this.menu();
        }
    }
}
