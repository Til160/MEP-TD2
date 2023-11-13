package org.polytech.covidapi.payload.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public class UpdateUserRequest {
    @NotBlank
    private String nom;
    @NotBlank
    private String prenom;
    @NotBlank
    private String telephone;
    @NotBlank
    private String email;

    private List<String> roles;
    
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getTelephone() {
        return telephone;
    }
    public String getEmail() {
        return email;
    }
    public List<String> getRoles() {
        return roles;
    }
}
