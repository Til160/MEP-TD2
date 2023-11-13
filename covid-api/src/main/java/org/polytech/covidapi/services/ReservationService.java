package org.polytech.covidapi.services;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.polytech.covidapi.entities.Centre;
import org.polytech.covidapi.entities.Reservation;
import org.polytech.covidapi.entities.Utilisateur;
import org.polytech.covidapi.repositories.CentreRepository;
import org.polytech.covidapi.repositories.ReservationRepository;
import org.polytech.covidapi.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private CentreRepository centreRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public boolean makeReservation(Integer idCentre, String userEmail, Timestamp date) {
        if(centreRepository.findById(idCentre).isEmpty() || utilisateurRepository.findByEmail(userEmail).isEmpty()) {
            return false;
        }
        Centre center = centreRepository.findById(idCentre).get();
        Utilisateur user = utilisateurRepository.findByEmail(userEmail).get();
        if(!validDate(date)) {
            return false;
        }
        if(reservationRepository.findByDateAndCentre(date, center).isPresent()) {
            return false;
        }       
        Reservation reservation = new Reservation(date, user, center);  
        reservationRepository.save(reservation);   
        return true;   
    }


    public List<Reservation> getReservationsFromToByCentre(Timestamp dateFrom, Timestamp dateTo, Integer idCentre) {
        return reservationRepository.findAllByDateBetweenAndCentre(dateFrom, dateTo, centreRepository.findById(idCentre).get());
    }

    private boolean validDate(Timestamp date) {
        // la date doit être un jour entre lundi et samedi et entre 8h et 18h. Elle doit également être tous les quarts d'heure. Elle doit aussi être supérieur au jour actuel.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());
        return dayOfWeek >= Calendar.MONDAY && dayOfWeek <= Calendar.SATURDAY
                && hourOfDay >= 8 && hourOfDay <= 18
                && minute % 15 == 0
                && calendar.after(now);
    }


    public List<Reservation> getReservationsByUser(String userEmail) {
        if(utilisateurRepository.findByEmail(userEmail).isEmpty()) {
            return null;
        }
        return reservationRepository.findAllByUtilisateur(utilisateurRepository.findByEmail(userEmail).get());
    }


    public boolean cancelReservation(Integer id, String userEmail) {
        if(utilisateurRepository.findByEmail(userEmail).isEmpty() || reservationRepository.findById(id).isEmpty()) {
            return false;
        }
        Utilisateur user = utilisateurRepository.findByEmail(userEmail).get();
        Reservation reservation = reservationRepository.findById(id).get();
        if(!reservation.getUtilisateur().equals(user)) {
            return false;
        }
        reservationRepository.delete(reservation);
        return true;
    }

    public List<Reservation> getReservationsForDayByCentre(Integer centreId, Timestamp date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Timestamp dateFrom = new Timestamp(calendar.getTimeInMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Timestamp dateTo = new Timestamp(calendar.getTimeInMillis());
        return reservationRepository.findAllByDateBetweenAndCentre(dateFrom, dateTo, centreRepository.findById(centreId).get());
    }
    
    public void deleteReservation(Integer id) {
        reservationRepository.deleteById(id);
    }
}
