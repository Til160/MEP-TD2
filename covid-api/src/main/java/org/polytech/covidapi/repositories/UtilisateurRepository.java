package org.polytech.covidapi.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.polytech.covidapi.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByEmail(String email);

    List<Utilisateur> findByEmailStartsWith(String mail);
}
