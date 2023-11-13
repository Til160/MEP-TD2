package org.polytech.covidapi.services;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.polytech.covidapi.entities.ERole;
import org.polytech.covidapi.entities.Utilisateur;
import org.polytech.covidapi.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UtilisateurService implements UserDetailsService{
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    public void addUser(String mail, String encodedPassword, String nom, String prenom, String telephone) {
        Utilisateur utilisateur = new Utilisateur(mail, nom, prenom, telephone, encodedPassword);
        utilisateurRepository.save(utilisateur);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return utilisateurRepository.findByEmail(username)
                .map(utilisateur -> new User(utilisateur.getEmail(), 
                                             utilisateur.getPassword(), 
                                             utilisateur.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.toString())).toList()))
                .orElseThrow(()-> new UsernameNotFoundException("Utilisateur non trouvé"));
    }

    public Optional<Utilisateur> findByEmail(String mail) {
        return utilisateurRepository.findByEmail(mail);
    }

    public void updateUser(Utilisateur utilisateur) {
        utilisateurRepository.save(utilisateur);
    }

    public List<Utilisateur> findByEmailStartsWith(String mail) {
        return utilisateurRepository.findByEmailStartsWith(mail);
    }

    public void updateUserRoles(String email, List<String> roles) {
        if(utilisateurRepository.findByEmail(email).isPresent()){
            Utilisateur utilisateur = utilisateurRepository.findByEmail(email).get();
            List<ERole> rolesArray = new ArrayList<ERole>();
            roles.forEach(role -> {
                rolesArray.add(ERole.valueOf(role));
            });
            utilisateur.setRoles(rolesArray);
            utilisateurRepository.save(utilisateur);
        }
    }

    public void deleteUser(Utilisateur utilisateur) {
        utilisateurRepository.delete(utilisateur);
    }
}
