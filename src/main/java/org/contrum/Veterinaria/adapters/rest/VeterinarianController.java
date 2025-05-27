package org.contrum.Veterinaria.adapters.rest;

import org.contrum.Veterinaria.adapters.rest.request.ClinicalRecordRequest;
import org.contrum.Veterinaria.adapters.rest.request.OrderRequest;
import org.contrum.Veterinaria.adapters.rest.request.PetOwnerRequest;
import org.contrum.Veterinaria.adapters.rest.request.PetRequest;
import org.contrum.Veterinaria.adapters.rest.response.ClinicalRecordResponse;
import org.contrum.Veterinaria.adapters.rest.response.OrderResponse;
import org.contrum.Veterinaria.adapters.rest.response.PersonResponse;
import org.contrum.Veterinaria.adapters.rest.response.PetResponse;
import org.contrum.Veterinaria.domain.models.ClinicalRecord;
import org.contrum.Veterinaria.domain.models.Order;
import org.contrum.Veterinaria.domain.models.Person;
import org.contrum.Veterinaria.domain.models.Pet;
import org.contrum.Veterinaria.domain.services.VeterinarianService;
import org.contrum.Veterinaria.exceptions.BusinessException;
import org.contrum.Veterinaria.exceptions.InputsException;
import org.contrum.Veterinaria.exceptions.NotFoundException;
import org.contrum.Veterinaria.utils.validators.ClinicalRecordValidator;
import org.contrum.Veterinaria.utils.validators.OrderValidator;
import org.contrum.Veterinaria.utils.validators.PersonValidator;
import org.contrum.Veterinaria.utils.validators.PetValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VeterinarianController {

    @Autowired
    private VeterinarianService veterinarianService;
    @Autowired
    private PersonValidator personValidator;
    @Autowired
    private PetValidator petValidator;
    @Autowired
    private ClinicalRecordValidator clinicalRecordValidator;
    @Autowired
    private OrderValidator orderValidator;

    @PostMapping("/petOwner")
    public ResponseEntity createSeller(@RequestBody PetOwnerRequest request) {
        try {
            String name = personValidator.nameValidator(request.getName());
            long document = personValidator.documentValidator(request.getDocument() + "");
            int age = personValidator.ageValidator(request.getAge() + "");

            Person person = new Person();
            person.setName(name);
            person.setDocument(document);
            person.setAge(age);
            person.setRole(Person.Role.PET_OWNER);

            veterinarianService.registerPetOwner(person);
            return new ResponseEntity("Se ha creado el petOwner", HttpStatus.OK);
        } catch (BusinessException be) {
            return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
        } catch (InputsException ie) {
            return new ResponseEntity(ie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/pet")
    public ResponseEntity createPet(@RequestBody PetRequest request) {
        try {
            String name = petValidator.nameValidator(request.getName());
            int age = petValidator.ageValidator(request.getAge() + "");
            String species = petValidator.specieValidator(request.getSpecies());
            String breed = petValidator.breedValidator(request.getBreed());
            String color = petValidator.colorValidator(request.getColor());
            int size = petValidator.sizeValidator(request.getSize() + "");
            int weight = petValidator.weightValidator(request.getWeight() + "");
            long ownerDocument = personValidator.documentValidator(request.getOwnerDocument() + "");

            Pet pet = new Pet();
            pet.setName(name);
            pet.setAge(age);
            pet.setSpecies(species);
            pet.setBreed(breed);
            pet.setColor(color);
            pet.setSize(size);
            pet.setWeight(weight);
            pet.setOwnerDocument(ownerDocument);

            veterinarianService.registerPet(pet);
            return new ResponseEntity("Se ha creado la mascota", HttpStatus.OK);
        } catch (BusinessException be) {
            return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
        } catch (InputsException ie) {
            return new ResponseEntity(ie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/clinicalRecord")
    public ResponseEntity createClinicalRecord(@RequestBody ClinicalRecordRequest request) {
        try {
            long veterinarianId = personValidator.idValidator(request.getVeterinarianId() + "");
            long petId = petValidator.idValidator(request.getPetId() + "");
            String reason = clinicalRecordValidator.reasonValidator(request.getReason());
            String symptom = request.getSymptom();
            String diagnostic = request.getDiagnostic();
            String procedure = request.getProcedure();
            String medicament = request.getMedicament();
            String vaccination = request.getVaccination();
            String allergicTo = request.getAllergicTo();
            String procedureDetail = request.getProcedureDetail();

            ClinicalRecord record = new ClinicalRecord();
            record.setVeterinarianId(veterinarianId);
            record.setPetId(petId);
            record.setReason(reason);
            record.setSymptom(symptom);
            record.setDiagnostic(diagnostic);
            record.setProcedure(procedure);
            record.setMedicament(medicament);
            record.setVaccination(vaccination);
            record.setAllergicTo(allergicTo);
            record.setProcedureDetail(procedureDetail);
            record.setTimestamp(System.currentTimeMillis());

            veterinarianService.createClinicalRecord(record);
            return new ResponseEntity("Se ha creado el registro clinico", HttpStatus.OK);
        } catch (BusinessException be) {
            return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
        } catch (InputsException ie) {
            return new ResponseEntity(ie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/order")
    public ResponseEntity createOrder(@RequestBody OrderRequest request) {
        try {
            long petId = petValidator.idValidator(request.getPetId() + "");
            Pet pet = veterinarianService.getPet(petId);
            if (pet == null) {
                throw new NotFoundException("No existe una mascota con la ID: " + petId);
            }

            long recordId = clinicalRecordValidator.idValidator(request.getRecordId() + "");
            long veterinarianId = personValidator.idValidator(request.getVeterinarianId() + "");
            String medicament = orderValidator.medicamentValidator(request.getMedicament());

            Order order = new Order();
            order.setRecordId(recordId);
            order.setPetId(pet.getId());
            order.setPetOwnerId(pet.getOwnerDocument());
            order.setVeterinarianId(veterinarianId);
            order.setMedicament(medicament);
            order.setTimestamp(System.currentTimeMillis());

            veterinarianService.createOrder(order);
            return new ResponseEntity("Se ha creado el registro clinico", HttpStatus.OK);
        } catch (BusinessException be) {
            return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
        } catch (InputsException ie) {
            return new ResponseEntity(ie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/order/cancel/{id}")
    public ResponseEntity cancelOrder(@PathVariable long id) {
        try {
            Order order = veterinarianService.getOrderById(id);
            if (order == null) {
                throw new NotFoundException("No existe una orden con la ID: " + id);
            }

            veterinarianService.cancelOrder(order);
            return new ResponseEntity("Orden cancelada exitosamente", HttpStatus.OK);
        } catch (NotFoundException NFe) {
            return new ResponseEntity(NFe.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/petOwner/{document}")
    public ResponseEntity getSeller(@PathVariable long document) {
        try {
            Person person = veterinarianService.getPetOwner(document);
            PersonResponse personResponse = new PersonResponse(person);
            return new ResponseEntity(personResponse, HttpStatus.FOUND);
        } catch (NotFoundException NFe) {
            return new ResponseEntity(NFe.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pet/{id}")
    public ResponseEntity getPet(@PathVariable long id) {
        try {
            Pet pet = veterinarianService.getPet(id);
            PetResponse petResponse = new PetResponse(pet);
            return new ResponseEntity(pet, HttpStatus.FOUND);
        } catch (NotFoundException NFe) {
            return new ResponseEntity(NFe.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pets/{document}")
    public ResponseEntity getPets(@PathVariable long document) {
        try {
            Person person = veterinarianService.getPetOwner(document);
            if (person == null) {
                throw new NotFoundException("Pet owner not found with document: " + document);
            }

            List<Pet> pets = veterinarianService.getPetsByOwnerId(person.getId());
            List<PetResponse> responses = pets.stream()
                    .map(PetResponse::new)
                    .toList();

            return new ResponseEntity(responses, HttpStatus.FOUND);
        } catch (NotFoundException NFe) {
            return new ResponseEntity(NFe.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/clinicalRecord/{id}")
    public ResponseEntity getClinicalRecord(@PathVariable long id) {
        try {
            ClinicalRecord record = veterinarianService.getClinicalRecord(id);
            ClinicalRecordResponse recordResponse = new ClinicalRecordResponse(record);
            return new ResponseEntity(recordResponse, HttpStatus.FOUND);
        } catch (NotFoundException NFe) {
            return new ResponseEntity(NFe.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/clinicalRecords/{petId}")
    public ResponseEntity getClinicalRecordsByPetId(@PathVariable long petId) {
        try {
            List<ClinicalRecord> records = veterinarianService.getClinicalRecordsByPetId(petId);
            List<ClinicalRecordResponse> responses = records.stream()
                    .map(ClinicalRecordResponse::new)
                    .toList();
            return new ResponseEntity(responses, HttpStatus.FOUND);
        } catch (NotFoundException NFe) {
            return new ResponseEntity(NFe.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}