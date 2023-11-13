package org.polytech.covidapi.services;

import java.util.List;
import java.util.Optional;

import org.polytech.covidapi.entities.Centre;
import org.polytech.covidapi.repositories.CentreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CentreService {
    @Autowired
    private CentreRepository centreRepository;
    
    public List<Centre> getCentresByCity(String ville) {
        return centreRepository.findAllByVille(ville);
    }

    public Optional<Centre> getCentreById(Integer id) {
        return centreRepository.findById(id);
    }

    public List<Centre> getAllCentres() {
        return centreRepository.findAll();
    }
    
    public void saveCentre(Centre centre){
        centreRepository.save(centre);
    }

    public void deleteCenter(Integer id) {
        centreRepository.deleteById(id);
    }
}


