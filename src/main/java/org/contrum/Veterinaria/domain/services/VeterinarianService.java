/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.contrum.Veterinaria.domain.services;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.contrum.Veterinaria.adapters.entities.seller.SellerAdapter;
import org.contrum.Veterinaria.domain.models.ClinicalRecord;
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
public class VeterinarianService {

    @Autowired
    private PersonPort personPort;
    @Autowired
    private UserPort userPort;
    @Autowired
    private SellerPort sellerPort;
    @Autowired
    private VeterinarianPort veterinarianPort;

    public void registerClinicalReport(ClinicalRecord record) {
        //TODO: Backsend save
    }

}
