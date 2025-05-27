package org.contrum.Veterinaria.adapters.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SellerRequest {
    private String name;
    private long document;
    private int age;
    private String userName;
    private String password;

    @Override
    public String toString() {
        return "SellerRequest{" +
                "name='" + name + '\'' +
                ", document=" + document +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
