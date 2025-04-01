package org.contrum.Veterinaria.adapters.inputs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.contrum.Veterinaria.adapters.entities.person.repository.PersonRepository;
import org.contrum.Veterinaria.adapters.entities.pet.repository.PetRepository;
import org.contrum.Veterinaria.domain.models.ClinicalRecord;
import org.contrum.Veterinaria.domain.models.Order;
import org.contrum.Veterinaria.domain.models.Person;
import org.contrum.Veterinaria.domain.models.Pet;
import org.contrum.Veterinaria.domain.services.LoginService;
import org.contrum.Veterinaria.domain.services.VeterinarianService;
import org.contrum.Veterinaria.ports.InputPort;
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
public class VeterinarianInput implements InputPort {
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
    private PersonRepository personRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private ConsolePaginator consolePaginator;

    public void menu() {
        Printer.print(
                "<border>",
                "<center>Menu Veterinario",
                "<center>Elige una opción",
                "<border>",
                "1. Registrar dueño de mascotas",
                "2. Registrar Mascota",
                "3. Listar dueños veterinarios",
                "4. Listar mascotas",
                "",
                "5. Crear registro clinico de mascota",
                "X. Volver"
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
                                    " Edad: " + person.getAge(),
                                    "<border>"
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
                                    "----------------------------------",
                                    " Id: " + pet.getId(),
                                    " Nombre: " + pet.getName(),
                                    " Dueño: " + pet.getOwner().getName() + " (Cedula: "+pet.getOwner().getDocument()+")",
                                    " Edad: " + pet.getAge() + " años",
                                    " Especie: " + pet.getSpecies(),
                                    " Raza: " + pet.getBreed(),
                                    "----------------------------------"
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
                case "x": {
                    return;
                }
                default:
                    System.out.println("opcion no valida");
            }
        } catch (Exception e) {
            e.printStackTrace(); // TODO: Remove
            Printer.print(e.getMessage());
        }
        menu();
    }

    private void createPerson() throws Exception {
        Printer.print("Ingrese el nombre de la persona");
        String name = personValidator.nameValidator(Printer.read());
        Printer.print("Ingrese el documento de la persona");
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

        Printer.print("Ingrese el tamaño de la mascota");
        int size = petValidator.sizeValidator(Printer.read());

        Printer.print("Ingrese el peso de la mascota");
        int weight = petValidator.weightValidator(Printer.read());

        Printer.print("Ingrese el documento del dueño de la mascota");
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

    private void createClinicalRecord() throws Exception {
        Printer.print("Ingrese el ID de la mascota");
        long petId = clinicalRecordValidator.petIdValidator(Printer.read());

        Printer.print("Ingrese el ID del veterinario");
        long veterinarianId = clinicalRecordValidator.veterinarianIdValidator(Printer.read());

        Printer.print("Ingrese la razón de la consulta");
        String reason = clinicalRecordValidator.reasonValidator(Printer.read());

        Printer.print("Ingrese los síntomas");
        String symptom = clinicalRecordValidator.symptomValidator(Printer.read());

        Printer.print("Ingrese el diagnóstico");
        String diagnostic = clinicalRecordValidator.diagnosticValidator(Printer.read());

        Printer.print("Ingrese el procedimiento");
        String procedure = clinicalRecordValidator.procedureValidator(Printer.read());

        Printer.print("Ingrese los medicamentos (dejar vacío si no hay)");
        String medicament = Printer.read();

        Printer.print("Ingrese la vacuna administrada (dejar vacío si no hay)");
        String vaccination = Printer.read();

        Printer.print("Ingrese alergias conocidas (dejar vacío si no hay)");
        String allergicTo = Printer.read();

        Printer.print("Ingrese detalles adicionales del procedimiento (dejar vacío si no hay)");
        String procedureDetail = Printer.read();

        ClinicalRecord record = new ClinicalRecord();
        record.setPetId(petId);
        record.setVeterinarianId(veterinarianId);
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

    private void createOrder() throws Exception {
        Printer.print("Ingrese el ID del registro clínico asociado");
        long recordId = orderValidator.recordIdValidator(Printer.read());

        Printer.print("Ingrese el ID de la mascota");
        long petId = orderValidator.petIdValidator(Printer.read());

        Printer.print("Ingrese el documento del dueño de la mascota");
        long petOwnerDocument = orderValidator.petOwnerDocumentValidator(Printer.read());

        Printer.print("Ingrese el documento del veterinario");
        long veterinarianDocument = orderValidator.veterinarianDocumentValidator(Printer.read());

        Printer.print("Ingrese el medicamento recetado");
        String medicament = orderValidator.medicamentValidator(Printer.read());

        Order order = new Order();
        order.setRecordId(recordId);
        order.setPetId(petId);
        order.setPetOwnerDocument(petOwnerDocument);
        order.setVeterinarianDocument(veterinarianDocument);
        order.setMedicament(medicament);
        order.setTimestamp(System.currentTimeMillis());

        //service.createOrder(order);

        Printer.print("\nOrden creada con éxito.");
    }
}