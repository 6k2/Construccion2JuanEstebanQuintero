/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.contrum.Veterinaria.domain.services;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.contrum.Veterinaria.adapters.entities.seller.SellerAdapter;
import org.contrum.Veterinaria.domain.models.Seller;
import org.contrum.Veterinaria.domain.models.Veterinarian;
import org.contrum.Veterinaria.ports.PersonPort;
import org.contrum.Veterinaria.ports.SellerPort;
import org.contrum.Veterinaria.ports.UserPort;
import org.contrum.Veterinaria.ports.VeterinarianPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Service
public class AdminService {

    @Autowired
    private PersonPort personPort;
    @Autowired
    private UserPort userPort;
    @Autowired
    private SellerPort sellerPort;
    @Autowired
    private VeterinarianPort veterinarianPort;

    /**
     * Register a new seller.
     * @param seller the seller to be registered.
     * @throws Exception if a person with the same document or a user with the same
     * username already exists.
     */
    public void registerSeller(Seller seller) throws Exception {
        if (personPort.existPerson(seller.getDocument())) {
            throw new Exception("Ya existe una persona con esa cedula");
        }
        if (userPort.existUserName(seller.getUserName())) {
            throw new Exception("Ya existe ese username registrado");
        }
        
        sellerPort.saveSeller(seller);
    }

    /**
     * Register a new veterinarian.
     * @param veterinarian the veterinarian to be registered.
     * @throws Exception if a person with the same document or a user with the same
     * username already exists.
     */
    public void registerVeterinarian(Veterinarian veterinarian) throws Exception {
        if (personPort.existPerson(veterinarian.getDocument())) {
            throw new Exception("Ya existe una persona con esa cedula");
        }
        if (userPort.existUserName(veterinarian.getUserName())) {
            throw new Exception("Ya existe ese username registrado");
        }

        veterinarianPort.saveVeterinarian(veterinarian);
    }
}