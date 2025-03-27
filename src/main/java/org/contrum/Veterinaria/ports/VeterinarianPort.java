package org.contrum.Veterinaria.ports;

import org.contrum.Veterinaria.domain.models.Veterinarian;

public interface VeterinarianPort {
    public void saveVeterinarian(Veterinarian seller);

    public Veterinarian findByVeterinarianId(Veterinarian seller);
}
