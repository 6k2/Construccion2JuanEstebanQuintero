package org.contrum.Veterinaria;

import org.contrum.Veterinaria.adapters.inputs.LoginInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class VeterinariaApplication implements CommandLineRunner {

	@Autowired
	private LoginInput loginInput;

	/**
	 * Starts the Spring Boot application.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(VeterinariaApplication.class, args);
	}
	/**
	 * Called when the application starts.
	 * Shows the login menu to the user.
	 *
	 * @param args the command line arguments.
	 * @throws Exception if any error occurs.
	 */
	@Override
	public void run(String... args) throws Exception {
		loginInput.menu();
	}
}
