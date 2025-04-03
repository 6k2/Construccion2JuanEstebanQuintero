package org.contrum.Veterinaria.ports;

import jakarta.annotation.Nullable;
import org.contrum.Veterinaria.domain.models.Veterinarian;

public interface VeterinarianPort {
    public void saveVeterinarian(Veterinarian veterinarian);

    public boolean existVeterinarianById(long document);

    @Nullable
    public Veterinarian findById(long id);
}
