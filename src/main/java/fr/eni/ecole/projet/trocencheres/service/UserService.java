package fr.eni.ecole.projet.trocencheres.service;

import fr.eni.ecole.projet.trocencheres.bo.Adresse;
import fr.eni.ecole.projet.trocencheres.bo.Utilisateur;
import fr.eni.ecole.projet.trocencheres.dto.UserProfile;
import fr.eni.ecole.projet.trocencheres.repository.AdresseRepository;
import fr.eni.ecole.projet.trocencheres.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UtilisateurRepository utilisateurRepository;
    private final AdresseRepository adresseRepository;

    public UserService(UtilisateurRepository utilisateurRepository, AdresseRepository adresseRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.adresseRepository = adresseRepository;
    }

    public UserProfile getUserProfile(String pseudo) {
        Optional<Utilisateur> uOpt = utilisateurRepository.findByPseudo(pseudo);
        if (!uOpt.isPresent()) return new UserProfile();
        Utilisateur u = uOpt.get();
        Adresse a = adresseRepository.findById(u.getNoAdresse()).orElse(null);
        return new UserProfile(u, a);
    }

    public boolean updateUserProfile(Utilisateur updatedUser, Adresse updatedAdresse) {
        // encode password if provided (not null/empty)
        if (updatedUser.getMotDePasse() != null && !updatedUser.getMotDePasse().isEmpty()) {
            // FRANCOIS : encoder le mot de passe ici
        } else {
            // keep existing password if empty in form
            utilisateurRepository.findByPseudo(updatedUser.getPseudo()).ifPresent(existing -> updatedUser.setMotDePasse(existing.getMotDePasse()));
        }

        int addrUpdated = adresseRepository.updateAdresse(updatedAdresse);
        int userUpdated = utilisateurRepository.updateUtilisateur(updatedUser);

        return (addrUpdated > 0 || userUpdated > 0);
    }
}
