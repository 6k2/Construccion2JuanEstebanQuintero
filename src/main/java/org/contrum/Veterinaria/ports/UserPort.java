/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package org.contrum.Veterinaria.ports;

import org.contrum.Veterinaria.domain.models.Person;
import org.contrum.Veterinaria.domain.models.User;

public interface UserPort {
    public boolean existUserName(String userName);

    public void saveUser(User user);

    public User findByPersonId(Person person);

	public User findByUserName(User user);
}
