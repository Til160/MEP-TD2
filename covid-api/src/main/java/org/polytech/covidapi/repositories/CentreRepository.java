package org.polytech.covidapi.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.polytech.covidapi.entities.Centre;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CentreRepository extends JpaRepository<Centre, Integer> {
    List<Centre> findAllByVille(String city);
}
