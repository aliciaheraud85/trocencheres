package fr.eni.ecole.projet.trocencheres.service;

import fr.eni.ecole.projet.trocencheres.bo.Adresse;
import fr.eni.ecole.projet.trocencheres.bo.Utilisateur;
import fr.eni.ecole.projet.trocencheres.dto.UserProfile;
import fr.eni.ecole.projet.trocencheres.repository.AdresseRepository;
import fr.eni.ecole.projet.trocencheres.repository.UtilisateurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UtilisateurRepository utilisateurRepository;
    private final AdresseRepository adresseRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UtilisateurRepository utilisateurRepository, AdresseRepository adresseRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.adresseRepository = adresseRepository;
        this.passwordEncoder = passwordEncoder;
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
        if (updatedUser != null && updatedUser.getMotDePasse() != null && !updatedUser.getMotDePasse().isEmpty()) {
            // encode raw password using injected PasswordEncoder
            String encoded = passwordEncoder.encode(updatedUser.getMotDePasse());
            updatedUser.setMotDePasse(encoded);
        } else {
            // keep existing password if empty in form
            if (updatedUser != null) {
                utilisateurRepository.findByPseudo(updatedUser.getPseudo()).ifPresent(existing -> updatedUser.setMotDePasse(existing.getMotDePasse()));
            }
        }

        // Ensure address id is propagated to the user before updating to avoid FK conflicts
        if (updatedUser != null) {
            // If the incoming user has no address id, prefer the one from the submitted adresse object
            if ((updatedUser.getNoAdresse() == 0) && (updatedAdresse != null) && (updatedAdresse.getNoAdresse() != 0)) {
                updatedUser.setNoAdresse(updatedAdresse.getNoAdresse());
            }

            // If still missing, try to read the existing stored user and reuse its address id
            if (updatedUser.getNoAdresse() == 0) {
                utilisateurRepository.findByPseudo(updatedUser.getPseudo()).ifPresent(existing -> {
                    updatedUser.setNoAdresse(existing.getNoAdresse());
                });
            }
        }

        int addrUpdated = 0;
        int userUpdated = 0;

        // If an address is provided but has no id, and it contains data, insert it first
        if (updatedAdresse != null && updatedAdresse.getNoAdresse() == 0) {
            boolean hasData = (updatedAdresse.getRue() != null && !updatedAdresse.getRue().isBlank())
                    || (updatedAdresse.getCodePostal() != null && !updatedAdresse.getCodePostal().isBlank())
                    || (updatedAdresse.getVille() != null && !updatedAdresse.getVille().isBlank());
            if (hasData) {
                int newId = adresseRepository.insertAdresse(updatedAdresse);
                if (newId != 0) {
                    if (updatedUser != null) {
                        updatedUser.setNoAdresse(newId);
                    }
                    addrUpdated = 1;
                }
            }
        } else if (updatedAdresse != null && updatedAdresse.getNoAdresse() != 0) {
            addrUpdated = adresseRepository.updateAdresse(updatedAdresse);
        }
        if (updatedUser != null) {
            userUpdated = utilisateurRepository.updateUtilisateur(updatedUser);
        }

        return (addrUpdated > 0 || userUpdated > 0);
    }
}
