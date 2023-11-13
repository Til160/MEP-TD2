package org.polytech.covidapi.services;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.polytech.covidapi.entities.Centre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CentreServiceIT {

    @Autowired
    private CentreService centreService;

    @Test
    public void getAllCentresTest () {
        assert(centreService.getAllCentres().size() > 0);
    }

    @Test
    public void getCentreByIdTest() {
        Optional<Centre> centre = centreService.getCentreById(1);
        assert(centre.isPresent());
        assert(centre.get().getId() == 1);
    }

    @Test
    public void getCentresByCityTest() {
        assert(centreService.getCentresByCity("Metz").size() > 0);
    }
    
    
}
