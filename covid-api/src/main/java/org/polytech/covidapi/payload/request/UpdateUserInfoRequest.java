package org.polytech.covidapi.payload.request;

import jakarta.validation.constraints.*;


public class UpdateUserInfoRequest {
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 50)
    private String nom;

    @NotBlank
    @Size(max = 50)
    private String prenom;

    @NotBlank
    @Size(max = 50)
    @Pattern(regexp = "^(0|\\+33|0033)[1-9][0-9]{8}$")
    private String telephone;

    public String getEmail() {
        return email;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getTelephone() {
        return telephone;
    }
    
}
