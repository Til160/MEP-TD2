package org.polytech.covidapi.entities;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Reservation {
    public Reservation() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @JsonProperty("date")
    private Timestamp date;

    @ManyToOne
    @JsonProperty("userId")
    private Utilisateur utilisateur;
    
    @ManyToOne
    @JsonProperty("centerId")
    private Centre centre;

    public Reservation(Timestamp date, Utilisateur utilisateur, Centre centre) {
        this.date = date;
        this.utilisateur = utilisateur;
        this.centre = centre;
    }

    public Timestamp getDate() {
        return date;
    }
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    public Centre getCentre() {
        return centre;
    }
    public Integer getId() {
        return id;
    }

}
