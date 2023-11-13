package org.polytech.covidapi.services;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.polytech.covidapi.entities.Utilisateur;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UtilisateurServiceTest {

    UtilisateurService utilisateurService;

    @BeforeEach
    void setUp() {
        utilisateurService = Mockito.mock(UtilisateurService.class);
    }

    @Test
    public void FindByEmailtest() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("email@email.fr");
        Mockito.doReturn(Optional.of(utilisateur)).when(utilisateurService).findByEmail("email@email.fr");

        Optional<Utilisateur> utilisateurTest = utilisateurService.findByEmail("email@email.fr");
        Assertions.assertThat(utilisateurTest.get().getEmail()).isEqualTo("email@email.fr");
    }

    @Test
    public void FindByEmailStartsWithtest() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("email@email.fr");
        Mockito.doReturn(List.of(utilisateur)).when(utilisateurService).findByEmailStartsWith("email");

        List<Utilisateur> utilisateurList = utilisateurService.findByEmailStartsWith("email");
        Assertions.assertThat(utilisateurList.size()>0);
    }

    @Test
    public void LoadUserByUsernametest() {
        UserDetails utilisateur = new User ("email", "password", List.of());
        Mockito.doReturn(utilisateur).when(utilisateurService).loadUserByUsername("email");

        UserDetails utilisateurTest = utilisateurService.loadUserByUsername("email");
        Assertions.assertThat(utilisateurTest.getUsername()).isEqualTo("email");
        Assertions.assertThat(utilisateurTest.getPassword()).isEqualTo("password");
    }
}
