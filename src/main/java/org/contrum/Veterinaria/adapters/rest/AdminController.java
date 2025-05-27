package org.contrum.Veterinaria.adapters.rest;

import org.contrum.Veterinaria.adapters.rest.request.SellerRequest;
import org.contrum.Veterinaria.adapters.rest.request.VeterinarianRequest;
import org.contrum.Veterinaria.adapters.rest.response.SellerResponse;
import org.contrum.Veterinaria.adapters.rest.response.VeterinarianResponse;
import org.contrum.Veterinaria.domain.models.Person;
import org.contrum.Veterinaria.domain.models.Seller;
import org.contrum.Veterinaria.domain.models.Veterinarian;
import org.contrum.Veterinaria.domain.services.AdminService;
import org.contrum.Veterinaria.exceptions.BusinessException;
import org.contrum.Veterinaria.exceptions.InputsException;
import org.contrum.Veterinaria.exceptions.NotFoundException;
import org.contrum.Veterinaria.utils.validators.PersonValidator;
import org.contrum.Veterinaria.utils.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private PersonValidator personValidator;
    @Autowired
    private UserValidator userValidator;

    @PostMapping("/seller")
    public ResponseEntity createSeller(@RequestBody SellerRequest request) {
        try {
            Seller seller = new Seller();

            String name = personValidator.nameValidator(request.getName());
            long document = personValidator.documentValidator(request.getDocument() + "");
            int age = personValidator.ageValidator(request.getAge() + "");
            String userName = userValidator.userNameValidator(request.getUserName());
            String password = userValidator.passwordValidator(request.getPassword());

            seller.setName(name);
            seller.setDocument(document);
            seller.setAge(age);
            seller.setUserName(userName);
            seller.setPassword(password);
            seller.setRole(Person.Role.SELLER);

            adminService.registerSeller(seller);
            return new ResponseEntity("Se ha creado el seller", HttpStatus.OK);
        } catch (BusinessException be) {
            return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
        } catch (InputsException ie) {
            return new ResponseEntity(ie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/veterinarian")
    public ResponseEntity createVeterinarian(@RequestBody VeterinarianRequest request) {
        try {
            Veterinarian veterinarian = new Veterinarian();

            String name = personValidator.nameValidator(request.getName());
            long document = personValidator.documentValidator(request.getDocument() + "");
            int age = personValidator.ageValidator(request.getAge() + "");
            String userName = userValidator.userNameValidator(request.getUserName());
            String password = userValidator.passwordValidator(request.getPassword());

            veterinarian.setName(name);
            veterinarian.setDocument(document);
            veterinarian.setAge(age);
            veterinarian.setUserName(userName);
            veterinarian.setPassword(password);
            veterinarian.setRole(Person.Role.VETERINARIAN);

            adminService.registerVeterinarian(veterinarian);
            return new ResponseEntity("Se ha creado el veterinario", HttpStatus.OK);
        } catch (BusinessException be) {
            return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
        } catch (InputsException ie) {
            return new ResponseEntity(ie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/seller/{document}")
    public ResponseEntity getSeller(@PathVariable long document) {
        try {
            Seller seller = adminService.getSeller(document);
            SellerResponse sellerResponse = new SellerResponse(seller);
            return new ResponseEntity(sellerResponse, HttpStatus.FOUND);
        } catch (NotFoundException NFe) {
            return new ResponseEntity(NFe.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/veterinarian/{document}")
    public ResponseEntity getVeterinarian(@PathVariable long document) {
        try {
            Veterinarian veterinarian = adminService.getVeterinarian(document);
            VeterinarianResponse veterinarianResponse = new VeterinarianResponse(veterinarian);
            return new ResponseEntity(veterinarianResponse, HttpStatus.FOUND);
        } catch (NotFoundException NFe) {
            return new ResponseEntity(NFe.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}