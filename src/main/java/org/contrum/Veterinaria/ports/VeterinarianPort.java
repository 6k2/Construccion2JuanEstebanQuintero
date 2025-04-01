package org.contrum.Veterinaria.ports;

import org.contrum.Veterinaria.domain.models.Veterinarian;

public interface VeterinarianPort {
    public void saveVeterinarian(Veterinarian seller);

    public boolean existVeterinarianByDocument(long document);

    public Veterinarian findByVeterinarianId(Veterinarian seller);
}
