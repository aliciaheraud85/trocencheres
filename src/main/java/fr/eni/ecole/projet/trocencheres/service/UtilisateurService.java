package fr.eni.ecole.projet.trocencheres.service;

import fr.eni.ecole.projet.trocencheres.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public boolean isPasswordValid(String pseudo, String testedPassword) {
        String actualPassword = utilisateurRepository.getPasswordByPseudo(pseudo);
        return Objects.equals(actualPassword, testedPassword);
    }

}
