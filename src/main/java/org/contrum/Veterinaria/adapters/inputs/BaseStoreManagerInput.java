package org.contrum.Veterinaria.adapters.inputs;

import org.antlr.v4.runtime.misc.NotNull;
import org.contrum.Veterinaria.domain.models.ClinicalRecord;
import org.contrum.Veterinaria.domain.models.Order;
import org.contrum.Veterinaria.domain.models.Person;
import org.contrum.Veterinaria.domain.models.Pet;
import org.contrum.Veterinaria.domain.services.VeterinarianService;
import org.contrum.Veterinaria.ports.*;
import org.contrum.Veterinaria.utils.ConsolePaginator;
import org.contrum.Veterinaria.utils.Printer;
import org.contrum.Veterinaria.utils.validators.ClinicalRecordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public abstract class BaseStoreManagerInput implements InputPort {

    @Autowired
    private ClinicalRecordValidator clinicalRecordValidator;

    @Autowired
    private VeterinarianService service;

    @Autowired
    private PersonPort personPort;
    @Autowired
    private PetPort petPort;
    @Autowired
    private OrderPort orderPort;
    @Autowired
    private ClinicalRecordPort clinicalRecordPort;

    @Autowired
    private ConsolePaginator consolePaginator;

    /**
     * Pide al usuario la cedula de un due o de mascota o la ID de una mascota,
     * y devuelve la mascota correspondiente.
     * Si la cedula ingresada es de un due o, se muestran las mascotas de ese
     * due o y se pide al usuario que elija una de ellas.
     * @return la mascota correspondiente a la cedula o ID ingresada
     * @throws Exception si no se encuentra ninguna persona con la cedula
     *                   ingresada o mascota con la ID ingresada, o si el usuario
     *                   ingresa un numero de mascota invalido.
     */
    protected Pet findPet() throws Exception {
        Printer.print("Ingrese la cedula de un dueño de mascota o una ID de mascota");
        long id = clinicalRecordValidator.documentOrPetIdValidator(Printer.read());

        Pet pet = service.findPetById(id);
        if (pet != null) {
            return pet;
        }

        Person person = personPort.findByDocument(id);
        if (person != null) {

            List<Pet> pets = petPort.findPetsByOwnerId(person.getId());
            if (pets.isEmpty()) {
                throw new Exception("La persona no tiene mascotas registradas!");
            }

            // Mostramos las mascotas de la persona elegida.
            Printer.print(
                    "<border>",
                    "<center>Mascotas de " + person.getName() + ":",
                    ""
            );

            for (int i = 0; i < pets.size(); i++) {
                Pet p = pets.get(i);
                Printer.print(i + ". Nombre: " + p.getName() + " - Especie: " + p.getSpecies() + " - Raza: " + p.getBreed() + " - Edad: " + p.getAge() + " años" + " (Id: "+p.getId()+")");
            }
            Printer.print("<border>");

            // Loop para que el veterinario elija una de las mascotas listadas.
            Pet choseenPet = null;
            while (choseenPet == null) {
                try {
                    Printer.print("Ingresa el numero de mascota a elegir:");
                    int petNumber = Integer.parseInt(Printer.read());
                    choseenPet = pets.get(petNumber);
                } catch (Exception e) {
                    Printer.print("Has ingresado un numero de mascota invalido!");
                }
            }

            return choseenPet;
        }

        throw new Exception("No se ha encontrado ninguna persona con esa cedula o mascota con esa ID.");
    }

    /**
     * Retrieves the most recent clinical record for a given pet. If multiple
     * records are available, prompts the user to select a specific record.
     *
     * @param pet the pet for which to retrieve the clinical record
     * @return the most recent or user-selected clinical record
     * @throws Exception if no clinical records are found for the given pet
     */
    protected ClinicalRecord getClinicalRecord(Pet pet) throws Exception {
        List<ClinicalRecord> clinicalRecords = clinicalRecordPort.findByPetId(pet.getId());

        if (clinicalRecords.isEmpty()) {
            throw new Exception("No se han encontrado historiales clinicos en esa mascota!");
        }

        ClinicalRecord latestRecord = null; // Encontrar el reporte mas reciente
        for (ClinicalRecord clinicalRecord : clinicalRecords) {
            if (latestRecord == null || latestRecord.getTimestamp() < clinicalRecord.getTimestamp()) {
                latestRecord = clinicalRecord;
            }
        }

        // Preguntar si se quiere usar un reporte diferente
        if (clinicalRecords.size() > 1) {
            Printer.print("Se ha encontrado un historial clinico del " + latestRecord.getFormattedTimeStampText() + " con la razón: '" + latestRecord.getReason() + "'");
            Printer.print("¿Desea crear la orden para ese historial? (Si/No)");

            boolean result = Printer.readBoolean();
            if (!result) {
                latestRecord = this.choseClinicalRecord(clinicalRecords);
            }
        }

        return latestRecord;
    }

    /**
     * Retrieves and displays all the orders associated with the most recent
     * clinical record for a given pet. If multiple clinical records are
     * available, prompts the user to select a specific record.
     *
     * @throws Exception if no clinical records or orders are found for the given pet
     */
    protected void listOrders() throws Exception {
        Pet pet = this.findPet();
        ClinicalRecord record = this.getClinicalRecord(pet);

        List<Order> orders = orderPort.findByClinicalRecordId(record.getId());
        consolePaginator.paginate(
                orders,
                order -> List.of(
                        "<border>",
                        " ID: " + order.getId(),
                        " ID del Registro Clínico: " + order.getRecordId(),
                        " ID de la Mascota: " + order.getPetId(),
                        " ID del Dueño: " + order.getPetOwnerId(),
                        " ID del Veterinario: " + order.getVeterinarianId(),
                        " Medicamento: " + order.getMedicament(),
                        " Cancelada: " + order.isCancelled(),
                        " Fecha: " + order.getFormattedTimeStampText()
                ),
                "Listado de órdenes", 10
        );
    }

    /**
     * Allows the user to choose an order from a list of given orders.
     *
     * <p>If <code>onlyActive</code> is <code>true</code>, this method will only
     * consider orders that are not cancelled.
     *
     * <p>This method will throw an exception if no orders are given or if all
     * orders are cancelled.
     *
     * @param orders the list of orders to choose from
     * @param onlyActive whether to only consider active orders
     * @return the chosen order
     * @throws Exception if no order can be chosen
     */
    protected Order choseOrder(List<Order> orders, boolean onlyActive) throws Exception {
        List<Order> filteredOrders = orders;
        if (onlyActive) {
            filteredOrders = new ArrayList<>(orders);
            filteredOrders.removeIf(order -> order == null || order.isCancelled());
        }

        if (filteredOrders.isEmpty()) {
            throw new Exception("No hay ordenes pendientes activas para esa mascota!");
        }

        // Mostramos las ordenes existentes
        Printer.print(
                "<border>",
                "<center>Ordenes Medicas",
                ""
        );

        for (int i = 0; i < filteredOrders.size(); i++) {
            Order order = orders.get(i);
            Printer.print(i + ". Fecha: " + order.getFormattedTimeStampBars() + " - Medicamento: " + order.getMedicament());
        }
        Printer.print("<border>");

        // Loop para que el vendedor elija uno de las ordenes listadas.
        Order choseenOrder = null;
        while (choseenOrder == null) {
            try {
                Printer.print("Ingresa el numero de la orden a elegir:");
                int recordNumber = Integer.parseInt(Printer.read());
                choseenOrder = orders.get(recordNumber);
            } catch (Exception e) {
                Printer.print("Has ingresado un numero de orden invalido!");
            }
        }

        return choseenOrder;
    }

    /**
     * Allows the user to choose a clinical record from a list of given records.
     *
     * <p>This method will throw an exception if no records are given.
     *
     * @param records the list of records to choose from
     * @return the chosen record
     * @throws Exception if no record can be chosen
     */
    protected ClinicalRecord choseClinicalRecord(List<ClinicalRecord> records) {
        // Mostramos los registros existentes
        Printer.print(
                "<border>",
                "<center>Registros clinicos",
                ""
        );
        for (int i = 0; i < records.size(); i++) {
            ClinicalRecord record = records.get(i);
            Printer.print(i + ". Fecha: " + record.getFormattedTimeStampBars() + " - Razón: " + record.getReason());
        }
        Printer.print("<border>");

        // Loop para que el veterinario elija uno de los registros listados.
        ClinicalRecord choseenRecord = null;
        while (choseenRecord == null) {
            try {
                Printer.print("Ingresa el numero del registro a elegir:");
                int recordNumber = Integer.parseInt(Printer.read());
                choseenRecord = records.get(recordNumber);
            } catch (Exception e) {
                Printer.print("Has ingresado un numero de registro invalido!");
            }
        }

        return choseenRecord;
    }
}
