package org.polytech.covidapi.controllers;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.polytech.covidapi.entities.Reservation;
import org.polytech.covidapi.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;


    @PostMapping(path="/makeReservation")
    public ResponseEntity<?> makeReservation(@RequestBody Map<String, Object> request){
        String userEmail = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Integer centreId = (Integer) request.get("centreId");
        Timestamp date = new Timestamp((Long) request.get("date"));
        if(reservationService.makeReservation(centreId,userEmail,date)){
            return ResponseEntity.ok().body("{ \"message\": \"Réservation effectuée avec succès\"}");
        }
        return ResponseEntity.badRequest().body("{ \"message\": \"Une erreur est survenue\"}");
    }

    @GetMapping(path="/getReservationsFromTo")
    public ResponseEntity<?> getReservations(@RequestParam long from, @RequestParam long to, @RequestParam Integer centreId){
        List<Reservation> reservations = reservationService.getReservationsFromToByCentre(new Timestamp(from), new Timestamp(to), centreId);
        return ResponseEntity.ok().body(reservations.stream().map(Reservation::getDate).map(Timestamp::getTime).toArray());
    }

    @GetMapping(path="/getReservationsByUser")
    public ResponseEntity<?> getReservationsByUser(){
        String userEmail = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        List<Reservation> reservations = reservationService.getReservationsByUser(userEmail);
        return ResponseEntity.ok().body(reservations.stream().map(reservation -> Map.of("id", reservation.getId(), "date", reservation.getDate().getTime(), "centre", reservation.getCentre())).toArray());
    }

    @DeleteMapping(path="/cancelReservation/{id}")
    public ResponseEntity<?> cancelReservation(@PathVariable Integer id){
        String userEmail = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        if(reservationService.cancelReservation(id, userEmail)){
            return ResponseEntity.ok().body("{ \"message\": \"Réservation annulée avec succès\"}");
        }
        return ResponseEntity.badRequest().body("{ \"message\": \"Une erreur est survenue\"}");
    }

    @GetMapping(path="/getReservationsForDayByCentre/{centreId}/{date}")
    public ResponseEntity<?> getReservationsForDayByCentre(@PathVariable Integer centreId, @PathVariable long date){
        List<Reservation> reservations = reservationService.getReservationsForDayByCentre(centreId, new Timestamp(date));
        return ResponseEntity.ok().body(reservations.stream().map(reservation -> Map.of("id", reservation.getId(), "date", reservation.getDate().getTime(), "centreId", reservation.getCentre().getId(), "utilisateur", reservation.getUtilisateur())).toArray());
    }



}
