package org.contrum.Veterinaria.domain.models;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Person {

	private long id;
	private long document;
	private String name;
	private int age;
	private Role role;

	/**
	 * Set the role of this person.
	 * @param role The role to set.
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * Set the role of this person.
	 * @param role The role to set, given as a string.
	 * This method is equivalent to calling {@link #setRole(Role)} with the result of {@link Role#valueOf(String)}.
	 */
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
