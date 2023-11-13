package org.polytech.covidapi.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Centre {
    public Centre() {
    }
    public Centre(String nom, String ville, String adresse, String codePostal) {
        this.nom = nom;
        this.ville = ville;
        this.adresse = adresse;
        this.codePostal = codePostal;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("nom")
    private String nom;

    @JsonProperty("adresse")
    private String adresse;

    @JsonProperty("codePostal")
    private String codePostal;

    @JsonProperty("ville")
    private String ville;

    @OneToMany(mappedBy = "centre")
    private List<Reservation> reservations;

    public String getNom() {
        return nom;
    }
    public String getAdresse() {
        return adresse;
    }
    public String getCodePostal() {
        return codePostal;
    }
    public String getVille() {
        return ville;
    }
    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }
}
