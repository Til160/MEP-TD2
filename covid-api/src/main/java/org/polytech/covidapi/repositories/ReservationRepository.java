package org.polytech.covidapi.repositories;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.List;

import org.polytech.covidapi.entities.Centre;
import org.polytech.covidapi.entities.Reservation;
import org.polytech.covidapi.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Optional<Reservation> findByDateAndCentre(Timestamp date, Centre center);

    List<Reservation> findAllByDateBetweenAndCentre(Timestamp dateFrom, Timestamp dateTo, Centre centre);

    List<Reservation> findAllByUtilisateur(Utilisateur utilisateur);

}
