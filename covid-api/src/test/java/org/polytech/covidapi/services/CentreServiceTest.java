package org.polytech.covidapi.services;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.polytech.covidapi.entities.Centre;

public class CentreServiceTest {

    CentreService centreService;

    @BeforeEach
    void setUp() {
        centreService = Mockito.mock(CentreService.class);
    }

    @Test
    public void getCentreByIdTest() {
        Centre centre = new Centre();
        centre.setId(1);
        Mockito.doReturn(Optional.of(centre)).when(centreService).getCentreById(1);

        Optional<Centre> centreTest = centreService.getCentreById(1);
        Assertions.assertThat(centreTest.get().getId()).isEqualTo(1);
    }

    @Test
    public void getAllCentresTest() {
        Centre centre1 = new Centre();
        centre1.setId(1);
        Centre centre2 = new Centre();
        centre2.setId(2);
        Mockito.doReturn(java.util.List.of(centre1, centre2)).when(centreService).getAllCentres();

        List<Centre> centres = centreService.getAllCentres();
        Assertions.assertThat(centres.size()).isEqualTo(2);
    }

    @Test
    public void getCentresByCityTest() {
        Centre centre1 = new Centre();
        centre1.setId(1);
        centre1.setVille("Metz");
        Centre centre2 = new Centre();
        centre2.setId(2);
        centre2.setVille("Metz");
        Centre centre3 = new Centre();
        centre3.setId(3);
        centre3.setVille("Nancy");
        Mockito.doReturn(java.util.List.of(centre1, centre2)).when(centreService).getCentresByCity("Metz");
        Mockito.doReturn(java.util.List.of(centre3)).when(centreService).getCentresByCity("Nancy");

        List<Centre> centres1 = centreService.getCentresByCity("Metz");
        List<Centre> centres2 = centreService.getCentresByCity("Nancy");
        Assertions.assertThat(centres1.size()).isEqualTo(2);
        Assertions.assertThat(centres2.size()).isEqualTo(1);
    }
}
