package org.contrum.Veterinaria.domain.models;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Person {

	private long personId;
	private long document;
	private String name;
	private int age;
	private Role role;

	public void setRole(Role role) {
		this.role = role;
	}

	public void setRole(String role) {
		this.role = Role.valueOf(role);
	}

	public enum Role {
		ADMINISTRATOR,
		VETERINARIAN,
		SELLER,
		PET_OWNER
	}
}
