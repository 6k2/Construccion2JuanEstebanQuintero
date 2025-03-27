package org.contrum.Veterinaria;

import org.contrum.Veterinaria.adapters.inputs.LoginInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VeterinariaApplication implements CommandLineRunner {

	@Autowired
	private LoginInput loginInput;

	public static void main(String[] args) {
		SpringApplication.run(VeterinariaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		loginInput.menu();
	}
}
