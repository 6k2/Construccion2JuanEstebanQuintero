package org.contrum.Veterinaria.adapters.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.contrum.Veterinaria.domain.models.Person;

@Getter @Setter @NoArgsConstructor
public class PersonResponse {
    private long id;
    private long document;
    private String name;
    private int age;
    private Person.Role role;

    public PersonResponse(Person person) {
        this.id = person.getId();
        this.document = person.getDocument();
        this.name = person.getName();
        this.age = person.getAge();
        this.role = person.getRole();
    }
}
