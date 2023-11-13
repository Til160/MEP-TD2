package org.polytech.covidapi.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UtilisateurServiceIT {
    
    @Autowired
    private UtilisateurService utilisateurService;

    @Test
    public void findByEmailTest() {
        assert(utilisateurService.findByEmail("admin@admin.fr").isPresent());
    }

    @Test
    public void findByEmailStartsWithTest() {
        assert(utilisateurService.findByEmailStartsWith("admin").size() > 0);
    }

    @Test
    public void loadUserByUsernameTest() {
        assert(utilisateurService.loadUserByUsername("admin@admin.fr").getUsername().equals("admin@admin.fr"));
    }
}
