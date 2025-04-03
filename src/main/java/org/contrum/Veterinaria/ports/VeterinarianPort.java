package org.contrum.Veterinaria.ports;

import jakarta.annotation.Nullable;
import org.contrum.Veterinaria.domain.models.Veterinarian;

public interface VeterinarianPort {
    /**
     * Saves a veterinarian to the database.
     * @param veterinarian the veterinarian to save.
     */
    public void saveVeterinarian(Veterinarian veterinarian);

    public boolean existVeterinarianById(long document);

    @Nullable
    public Veterinarian findById(long id);
}
