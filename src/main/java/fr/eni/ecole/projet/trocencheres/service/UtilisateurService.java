package fr.eni.ecole.projet.trocencheres.service;

import fr.eni.ecole.projet.trocencheres.bo.Utilisateur;
import fr.eni.ecole.projet.trocencheres.repository.UtilisateurRepository;

import java.util.Objects;

public class UserService {


    private final UtilisateurRepository utilisateurRepository;

    public UserService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public boolean isPasswordValid(String pseudo, String testedPassword) {
        String actualPassword = utilisateurRepository.getPasswordByPseudo(pseudo);
        return Objects.equals(actualPassword, testedPassword);
    }

}
