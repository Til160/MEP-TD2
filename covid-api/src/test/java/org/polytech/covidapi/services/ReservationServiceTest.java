package org.polytech.covidapi.services;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.polytech.covidapi.entities.Reservation;

public class ReservationServiceTest {

    ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationService = Mockito.mock(ReservationService.class);
    }

    @Test
    void GetReservationsByUserTest() {
        List<Reservation> List = new ArrayList<Reservation>() {{
            add(new Reservation());
        }};
        Mockito.doReturn(List).when(reservationService).getReservationsByUser("email");
        reservationService.getReservationsByUser("email");
        
        Assertions.assertThat(reservationService.getReservationsByUser("email").size() > 0);
    }

    @Test
    void GetReservationsFromToByCentreTest() {
        Timestamp datefrom = new Timestamp(0);
        Timestamp dateto = new Timestamp(10);
        List<Reservation> List = new ArrayList<Reservation>() {{
            add(new Reservation());
        }};
        Mockito.doReturn(List).when(reservationService).getReservationsFromToByCentre(datefrom, dateto, 1);
        reservationService.getReservationsFromToByCentre(datefrom, dateto, 1);
        
        Assertions.assertThat(reservationService.getReservationsFromToByCentre(datefrom, dateto, 1).size() > 0);
    }
}
