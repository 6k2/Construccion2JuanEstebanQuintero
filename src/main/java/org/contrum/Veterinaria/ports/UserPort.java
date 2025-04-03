/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package org.contrum.Veterinaria.ports;

import org.contrum.Veterinaria.domain.models.Person;
import org.contrum.Veterinaria.domain.models.User;

public interface UserPort {
    /**
     * Checks if a user exists with the given username.
     *
     * @param userName The username to search for.
     * @return True if a user with the given username exists, false otherwise.
     */
    public boolean existUserName(String userName);

    public void saveUser(User user);

    public User findByPersonId(Person person);

    /**
     * Finds a user by username.
     *
     * @param user The user to search for, containing the username to search by.
     * @return The user found, or null if no such user exists.
     */
	public User findByUserName(User user);
}
