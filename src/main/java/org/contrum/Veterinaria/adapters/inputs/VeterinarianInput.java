package org.contrum.Veterinaria.adapters.inputs;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.contrum.Veterinaria.adapters.entities.person.repository.PersonRepository;
import org.contrum.Veterinaria.adapters.entities.pet.repository.PetRepository;
import org.contrum.Veterinaria.domain.models.*;
import org.contrum.Veterinaria.domain.services.LoginService;
import org.contrum.Veterinaria.domain.services.VeterinarianService;
import org.contrum.Veterinaria.ports.*;
import org.contrum.Veterinaria.utils.ConsolePaginator;
import org.contrum.Veterinaria.utils.Printer;
import org.contrum.Veterinaria.utils.validators.ClinicalRecordValidator;
import org.contrum.Veterinaria.utils.validators.OrderValidator;
import org.contrum.Veterinaria.utils.validators.PersonValidator;
import org.contrum.Veterinaria.utils.validators.PetValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Component
public class VeterinarianInput extends BaseStoreManagerInput {
    @Autowired
    private PersonValidator personValidator;
    @Autowired
    private PetValidator petValidator;
    @Autowired
    private ClinicalRecordValidator clinicalRecordValidator;
    @Autowired
    private OrderValidator orderValidator;

    @Autowired
    private LoginService loginService;
    @Autowired
    private VeterinarianService service;

    @Autowired
    private PersonPort personPort;
    @Autowired
    private VeterinarianPort veterinarianPort;
    @Autowired
    private PetPort petPort;
    @Autowired
    private ClinicalRecordPort clinicalRecordPort;
    @Autowired
    private OrderPort orderPort;

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private ConsolePaginator consolePaginator;

    /**
     * Displays the veterinarian menu and prompts the user to select an option.
     * <p>
     * This method presents a menu with various options related to managing pet
     * owners and pets, such as registering a pet owner or pet, listing pet
     * owners or pets, creating clinical records or orders, and consulting or
     * canceling orders.
     * <p>
     * Depending on the user's choice, the appropriate method is invoked to
     * perform the desired action. The menu is displayed again after each action
     * until the user chooses to close the session by entering 'X'.
     * <p>
     * If an exception occurs during the execution of any action, the error
     * message is printed and the menu is displayed again.
     */
    public void menu() {
        Printer.print(
                "<border>",
                "<center>Menu Veterinario",
                "<center>Elige una opción",
                "<border>",
                "1. Registrar dueño de mascotas",
                "2. Registrar Mascota",
                "3. Listar dueños de mascotas",
                "4. Listar mascotas",
                "",
                "5. Crear registro clinico de mascota",
                "6. Crear orden",
                "7. Cancelar orden",
                "8. Consultar registros clinicos de mascota",
                "9. Consultar ordenes medicas de mascota",
                "",
                "X. Cerrar sesion"
        );

        String choice = Printer.read().toLowerCase();
        try {
            switch (choice) {
                case "1": {
                    this.createPerson();
                    Printer.print("\nPersona registrada con exito!");

                    break;
                }
                case "2": {
                    this.createPet();
                    Printer.print("\nMascota registrada con exito!");
                    break;
                }
                case "3": {
                    consolePaginator.paginate(
                            personRepository,
                            vet -> vet.getRole().equals(Person.Role.PET_OWNER.name()),
                            person -> List.of(
                                    "<border>",
                                    " Id: " + person.getId(),
                                    " Nombre: " + person.getName(),
                                    " Cédula: " + person.getDocument(),
                                    " Edad: " + person.getAge()
                            ),
                            "Listado de dueños de mascotas",
                            10
                    );
                    break;
                }
                case "4": {
                    consolePaginator.paginate(
                            petRepository,
                            pet -> List.of(
                                    "<border>",
                                    " Id: " + pet.getId(),
                                    " Nombre: " + pet.getName(),
                                    " Dueño: " + pet.getOwner().getName() + " (Cedula: "+pet.getOwner().getDocument()+")",
                                    " Edad: " + pet.getAge() + " años",
                                    " Especie: " + pet.getSpecies(),
                                    " Raza: " + pet.getBreed(),
                                    " Color: " + pet.getColor(),
                                    " Tamaño: " + pet.getSize() + " cm",
                                    " Peso: " + pet.getWeight() + " kg"
                            ),
                            "Listado de mascotas",
                            10
                    );
                    break;
                }
                case "5": {
                    this.createClinicalRecord();
                    Printer.print("\nRegistro clínico creado con éxito.");
                    break;
                }
                case "6": {
                    this.createOrder(null, null, null);
                    Printer.print("\nOrden creada con éxito.");
                    break;
                }
                case "7": {
                    this.cancelOrder();
                    Printer.print("\nOrden cancelada con exito.");
                    break;
                }
                case "8": {
                    this.listClinicalRecords();
                    break;
                }
                case "9": {
                    super.listOrders();
                    break;
                }
                case "x": {
                    return;
                }
                default:
                    System.out.println("opcion no valida");
            }
        } catch (Exception e) {
            Printer.print(e.getMessage());
        }
        menu();
    }

    /**
     * Registers a new pet owner with the given name, document ID, and age.
     *
     * This method prompts the user to input the pet owner's name, document ID,
     * and age. It validates the input data and creates a new {@link Person} object
     * with the provided information. The pet owner is then registered using the
     * {@link PersonService}.
     *
     * @throws Exception if a person with the same document already exists.
     */
    private void createPerson() throws Exception {
        Printer.print("Ingrese el nombre de la persona");
        String name = personValidator.nameValidator(Printer.read());
        Printer.print("Ingrese la cedula de la persona");
        long document = personValidator.documentValidator(Printer.read());
        Printer.print("Ingrese la edad de la persona");
        int age = personValidator.ageValidator(Printer.read());

        Person person = new Person();
        person.setName(name);
        person.setDocument(document);
        person.setAge(age);
        person.setRole(Person.Role.PET_OWNER);

        service.registerPetOwner(person);
    }

        /**
         * Registers a new pet with the given name, age, species, breed, color,
         * size, weight, and owner document ID.
         *
         * This method prompts the user to input the pet's name, age, species,
         * breed, color, size, weight, and owner document ID. It validates the
         * input data and creates a new {@link Pet} object with the provided
         * information. The pet is then registered using the
         * {@link PetService}.
         *
         * @throws Exception if a pet with the same name already exists.
         */
    private void createPet() throws Exception {
        Printer.print("Ingrese el nombre de la mascota");
        String name = petValidator.nameValidator(Printer.read());

        Printer.print("Ingrese la edad de la mascota");
        int age = petValidator.ageValidator(Printer.read());

        Printer.print("Ingrese la especie de la mascota");
        String species = petValidator.specieValidator(Printer.read());

        Printer.print("Ingrese la raza de la mascota");
        String breed = petValidator.breedValidator(Printer.read());

        Printer.print("Ingrese el color de la mascota");
        String color = petValidator.colorValidator(Printer.read());

        Printer.print("Ingrese el tamaño de la mascota en cm");
        int size = petValidator.sizeValidator(Printer.read());

        Printer.print("Ingrese el peso de la mascota en kg");
        int weight = petValidator.weightValidator(Printer.read());

        Printer.print("Ingrese la cedula del dueño de la mascota");
        long ownerDocument = personValidator.documentValidator(Printer.read());

        Pet pet = new Pet();
        pet.setName(name);
        pet.setAge(age);
        pet.setSpecies(species);
        pet.setBreed(breed);
        pet.setColor(color);
        pet.setSize(size);
        pet.setWeight(weight);
        pet.setOwnerDocument(ownerDocument);

        service.registerPet(pet);
    }

        /**
         * Registers a new clinical record with the given reason, symptoms, diagnosis,
         * procedure, medications, vaccinations, allergies, and procedure details.
         *
         * This method prompts the user to input the clinical record's reason, symptoms,
         * diagnosis, procedure, medications, vaccinations, allergies, and procedure
         * details. It validates the input data and creates a new {@link ClinicalRecord}
         * object with the provided information. The clinical record is then registered
         * using the {@link ClinicalRecordService}.
         *
         * @throws Exception if the current user is not a veterinarian.
         */
    private void createClinicalRecord() throws Exception {
        Pet pet = this.findPet();

        // Para evitar pedir el ID del veterinario, buscamos la ID del usuario actual que ha iniciado sesión en LoginService
        // Tiene que ser un veterinario, ya que de lo contrario no podría haber accedido a este menú, pero retornamos un error
        // Inesperado en caso de que no lo sea.
        long currentUserId = loginService.getCurrentLoggedUser().getId();
        Veterinarian veterinarian = veterinarianPort.findById(currentUserId);
        if (veterinarian == null) {
            throw new Exception("Se ha producido un error inesperado ¿Has iniciado sesión como un veterinario?");
        }

        Printer.print("Ingrese la razón de la consulta");
        String reason = clinicalRecordValidator.reasonValidator(Printer.read());

        Printer.print("Ingrese los síntomas (dejar vacío si no hay)");
        String symptom = Printer.read();

        Printer.print("Ingrese el diagnóstico (dejar vacío si no hay)");
        String diagnostic = Printer.read();

        Printer.print("Ingrese el procedimiento (dejar vacío si no hay)");
        String procedure = Printer.read();

        Printer.print("Ingrese los medicamentos (dejar vacío si no hay)");
        String medicament = Printer.read();

        Printer.print("Ingrese la vacuna administrada (dejar vacío si no hay)");
        String vaccination = Printer.read();

        Printer.print("Ingrese alergias conocidas (dejar vacío si no hay)");
        String allergicTo = Printer.read();

        Printer.print("Ingrese detalles adicionales del procedimiento (dejar vacío si no hay)");
        String procedureDetail = Printer.read();

        ClinicalRecord record = new ClinicalRecord();
        record.setVeterinarianId(veterinarian.getId());
        record.setPetId(pet.getId());
        record.setReason(reason);
        record.setSymptom(symptom);
        record.setDiagnostic(diagnostic);
        record.setProcedure(procedure);
        record.setMedicament(medicament);
        record.setVaccination(vaccination);
        record.setAllergicTo(allergicTo);
        record.setProcedureDetail(procedureDetail);
        record.setTimestamp(System.currentTimeMillis());

        service.createClinicalRecord(record);
    }

        /**
         * Registers a new order for a pet with the given clinical record, pet, and veterinarian.
         *
         * This method creates a new {@link Order} object with the provided information and
         * registers it using the {@link OrderService}.
         *
         * If the clinical record is null, it prompts the user to input the clinical record ID
         * and finds the corresponding clinical record using the {@link ClinicalRecordService}.
         * If the pet is null, it prompts the user to input the pet ID and finds the corresponding
         * pet using the {@link PetService}. If the veterinarian is null, it finds the current
         * user's ID using the {@link LoginService} and finds the corresponding veterinarian
         * using the {@link VeterinarianService}.
         *
         * @param record the clinical record to be associated with the order.
         * @param pet the pet to be associated with the order.
         * @param veterinarian the veterinarian to be associated with the order.
         * @throws Exception if the user is not a veterinarian, or if the clinical record ID
         *                    does not correspond to an existing clinical record, or if the pet
         *                    ID does not correspond to an existing pet.
         */
    private void createOrder(@Nullable ClinicalRecord record, @Nullable Pet pet, @Nullable Veterinarian veterinarian) throws Exception {
        if (pet == null) {
            pet = super.findPet();
        }

        if (veterinarian == null) {
            // Para evitar pedir el ID del veterinario, buscamos la ID del usuario actual que ha iniciado sesión en LoginService
            // Tiene que ser un veterinario, ya que de lo contrario no podría haber accedido a este menú, pero retornamos un error
            // Inesperado en caso de que no lo sea.
            long currentUserId = loginService.getCurrentLoggedUser().getId();

            veterinarian = veterinarianPort.findById(currentUserId);
            if (veterinarian == null) {
                throw new Exception("Se ha producido un error inesperado ¿Has iniciado sesión como un veterinario?");
            }
        }


        if (record == null) {
            record = super.getClinicalRecord(pet);
        }

        Printer.print("Ingrese el medicamento recetado");
        String medicament = orderValidator.medicamentValidator(Printer.read());

        Order order = new Order();
        order.setRecordId(record.getId());
        order.setPetId(pet.getId());
        order.setPetOwnerId(pet.getOwnerDocument());
        order.setVeterinarianId(veterinarian.getId());
        order.setMedicament(medicament);
        order.setTimestamp(System.currentTimeMillis());

        service.createOrder(order);
    }

        /**
         * Lists all the clinical records associated with a given pet.
         *
         * @throws Exception if the user is not a veterinarian, or if the pet ID does not correspond to an existing pet.
         */
    private void listClinicalRecords() throws Exception {
        Pet pet = super.findPet();

        List<ClinicalRecord> records = clinicalRecordPort.findByPetId(pet.getId());
        consolePaginator.paginate(
                records,
                record -> List.of(
                        "<border>",
                        " Fecha: " + record.getFormattedTimeStampText(),
                        " Veterinario ID: " + record.getVeterinarianId(),
                        " Mascota ID: " + record.getPetId(),
                        " Razón: " + record.getReason(),
                        " Síntomas: " + record.getSymptom(),
                        " Diagnóstico: " + record.getDiagnostic(),
                        " Procedimiento: " + record.getProcedure(),
                        " Medicamentos: " + record.getMedicament(),
                        " Vacunación: " + record.getVaccination(),
                        " Alergias: " + record.getAllergicTo(),
                        " Detalles del procedimiento: " + record.getProcedureDetail()
                ),
                "Listado de registros clinicos",
                10
        );
    }

        /**
         * Cancela una orden pendiente para una mascota.
         *
         * Si no hay ordenes pendientes para esa mascota, lanza una excepción.
         *
         * @throws Exception si la mascota no tiene ordenes pendientes, o si se produce un error al cancelar la orden.
         */
    private void cancelOrder() throws Exception {
        Pet pet = super.findPet();
        List<Order> orders = orderPort.findByPetId(pet.getId());
        if (orders.isEmpty()) {
            throw new Exception("No hay ordenes pendientes para esa mascota!");
        }

        Order order = super.choseOrder(orders, true);
        order.setCancelled(true);
        orderPort.saveOrder(order);
    }
}