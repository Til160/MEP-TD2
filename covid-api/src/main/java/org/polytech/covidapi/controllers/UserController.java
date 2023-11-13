package org.polytech.covidapi.controllers;

import java.util.Map;

import org.polytech.covidapi.entities.Utilisateur;
import org.polytech.covidapi.payload.request.UpdateUserInfoRequest;
import org.polytech.covidapi.payload.request.UpdateUserPasswordRequest;
import org.polytech.covidapi.payload.request.UpdateUserRequest;
import org.polytech.covidapi.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path="/api/user")
public class UserController {
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    PasswordEncoder passwordEncoder;


    @GetMapping(path = "/getUserInfos")
    public ResponseEntity<?> getUserInfos(){
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(utilisateurService.findByEmail(user.getUsername()).isPresent()){
            Utilisateur utilisateur = utilisateurService.findByEmail(user.getUsername()).get();
            return ResponseEntity.ok().body("{ \"nom\": \""+ utilisateur.getNom()+"\", \"prenom\": \""+ utilisateur.getPrenom()+"\", \"telephone\": \""+ utilisateur.getTelephone()+"\", \"email\": \""+ utilisateur.getEmail()+"\"}");
        }
        return ResponseEntity.badRequest().body("{ \"message\": \"Utilisateur non trouvé\"}");
    }

    @PostMapping(path = "/updateUserInfos")
    public ResponseEntity<?> updateUserInfos(@Valid @RequestBody  UpdateUserInfoRequest updateUserInfoRequest, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors("telephone")){
            return ResponseEntity.badRequest().body("{ \"message\": \"Veuillez entrer un format de téléphone supporté\"}");
        }
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("{ \"message\": \"Veuillez remplir tous les champs\"}");
        }
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(utilisateurService.findByEmail(user.getUsername()).isPresent()){
            Utilisateur utilisateur = utilisateurService.findByEmail(user.getUsername()).get();
            utilisateur.setNom(updateUserInfoRequest.getNom());
            utilisateur.setPrenom(updateUserInfoRequest.getPrenom());
            utilisateur.setTelephone(updateUserInfoRequest.getTelephone());
            utilisateurService.updateUser(utilisateur);
            return ResponseEntity.ok().body("{ \"message\": \"Utilisateur mis à jour avec succès\"}");
        }
        return ResponseEntity.badRequest().body("{ \"message\": \"Utilisateur non trouvé\"}");
    }

    @PostMapping(path = "/updateUserPassword")
    public ResponseEntity<?> updateUserPassword(@Valid @RequestBody  UpdateUserPasswordRequest updateUserPasswordRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("{ \"message\": \"Les môts de passes doivent faire entre 6 et 40 caractères\"}");
        }
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(utilisateurService.findByEmail(user.getUsername()).isPresent()){
            Utilisateur utilisateur = utilisateurService.findByEmail(user.getUsername()).get();
            if(passwordEncoder.matches(updateUserPasswordRequest.getOldPassword(), utilisateur.getPassword())){
                utilisateur.setPassword(passwordEncoder.encode(updateUserPasswordRequest.getNewPassword()));
                utilisateurService.updateUser(utilisateur);
                return ResponseEntity.ok().body("{ \"message\": \"Mot de passe mis à jour avec succès\"}");
            }
            return ResponseEntity.badRequest().body("{ \"message\": \"Mot de passe incorrect\"}");
        }
        return ResponseEntity.badRequest().body("{ \"message\": \"Utilisateur non trouvé\"}");
    }

    @PostMapping(path = "/admin/searchUser")
    public ResponseEntity<?> searchUser(@RequestParam String email){
        return ResponseEntity.ok().body(utilisateurService.findByEmailStartsWith(email).stream().map(utilisateur -> Map.of("nom", utilisateur.getNom(), "prenom", utilisateur.getPrenom(), "telephone", utilisateur.getTelephone(), "email", utilisateur.getEmail(), "roles", utilisateur.getRoles())).toArray());
    }

    @PostMapping(path = "/admin/updateUser")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("{ \"message\": \"Veuillez remplir tous les champs\"}");
        }
        if(updateUserRequest.getRoles().isEmpty()){
            return ResponseEntity.badRequest().body("{ \"message\": \"Veuillez choisir au moins un rôle\"}");
        }
        utilisateurService.updateUserRoles(updateUserRequest.getEmail(), updateUserRequest.getRoles());
        return ResponseEntity.ok().body("{ \"message\": \"Utilisateur mis à jour avec succès\"}");
    }

    @PostMapping(path = "/admin/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestParam String email){
        if(utilisateurService.findByEmail(email).isPresent()){
            utilisateurService.deleteUser(utilisateurService.findByEmail(email).get());
            return ResponseEntity.ok().body("{ \"message\": \"Utilisateur supprimé avec succès\"}");
        }
        return ResponseEntity.badRequest().body("{ \"message\": \"Utilisateur non trouvé\"}");
    }

}
