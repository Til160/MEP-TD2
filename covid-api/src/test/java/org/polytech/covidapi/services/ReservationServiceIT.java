package org.polytech.covidapi.services;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.polytech.covidapi.entities.Reservation;
import org.polytech.covidapi.repositories.CentreRepository;
import org.polytech.covidapi.repositories.ReservationRepository;
import org.polytech.covidapi.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReservationServiceIT {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired 
    private CentreRepository centreRepository;

    @Autowired 
    private UtilisateurRepository utilisateurRepository;

    @Test
    void GetReservationsByUserTest() {
        Reservation r1 = new Reservation(new Timestamp(0),utilisateurRepository.findByEmail("utilisateur@utilisateur.fr").get(),centreRepository.findById(1).get());
        reservationRepository.save(r1);
        assert(reservationService.getReservationsByUser("utilisateur@utilisateur.fr").size() > 0);
        reservationRepository.delete(r1);
    }

    @Test
    void GetReservationsFromToByCentreTest() {
        Timestamp datefrom = new Timestamp(0);
        Timestamp dateto = new Timestamp(10);
        Reservation r1 = new Reservation(new Timestamp(0),utilisateurRepository.findByEmail("utilisateur@utilisateur.fr").get(),centreRepository.findById(1).get());
        reservationRepository.save(r1);

        assert(reservationService.getReservationsFromToByCentre(datefrom, dateto, 1).size() > 0);

    }
}

