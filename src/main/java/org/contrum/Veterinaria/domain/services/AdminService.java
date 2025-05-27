/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.contrum.Veterinaria.domain.services;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.contrum.Veterinaria.domain.models.Person;
import org.contrum.Veterinaria.domain.models.Seller;
import org.contrum.Veterinaria.domain.models.Veterinarian;
import org.contrum.Veterinaria.exceptions.BusinessException;
import org.contrum.Veterinaria.exceptions.NotFoundException;
import org.contrum.Veterinaria.ports.PersonPort;
import org.contrum.Veterinaria.ports.SellerPort;
import org.contrum.Veterinaria.ports.UserPort;
import org.contrum.Veterinaria.ports.VeterinarianPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * @throws BusinessException if a person with the same document or a user with the same
     * username already exists.
     */
    public void registerSeller(Seller seller) throws BusinessException {
        if (personPort.existPerson(seller.getDocument())) {
            throw new BusinessException("Ya existe una persona con esa cedula");
        }
        if (userPort.existUserName(seller.getUserName())) {
            throw new BusinessException("Ya existe ese username registrado");
        }
        
        sellerPort.saveSeller(seller);
    }

    /**
     * Register a new veterinarian.
     * @param veterinarian the veterinarian to be registered.
     * @throws BusinessException if a person with the same document or a user with the same
     * username already exists.
     */
    public void registerVeterinarian(Veterinarian veterinarian) throws BusinessException {
        if (personPort.existPerson(veterinarian.getDocument())) {
            throw new BusinessException("Ya existe una persona con esa cedula");
        }
        if (userPort.existUserName(veterinarian.getUserName())) {
            throw new BusinessException("Ya existe ese username registrado");
        }

        veterinarianPort.saveVeterinarian(veterinarian);
    }

    public Seller getSeller(long document) throws NotFoundException {
        Person person = personPort.findByDocument(document);
        if (person == null) {
            throw new NotFoundException("No se encontro una persona con el documento: " + document);
        }

        Seller seller = sellerPort.findById(person.getId());
        if (seller == null) {
            throw new NotFoundException("No se encontro un vendedor con el documento: " + document);
        }
        return seller;
    }

    public Veterinarian getVeterinarian(long document) throws NotFoundException {
        Person person = personPort.findByDocument(document);
        if (person == null) {
            throw new NotFoundException("No se encontro una persona con el documento: " + document);
        }

        Veterinarian veterinarian = veterinarianPort.findById(person.getId());
        if (veterinarian == null) {
            throw new NotFoundException("No se encontro un veterinario con el documento: " + document);
        }
        return veterinarian;
    }
}