package fr.eni.ecole.projet.trocencheres.controller;

import fr.eni.ecole.projet.trocencheres.bo.Adresse;
import fr.eni.ecole.projet.trocencheres.bo.Utilisateur;
import fr.eni.ecole.projet.trocencheres.dto.UserProfile;
import fr.eni.ecole.projet.trocencheres.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/profile")
    public String profile(Authentication authentication, Model model) {
        // FRANCOIS : implemente ta méthode pour récupérer l'utilisateur authentifié
        String username = authentication.getName();
        UserProfile profile = userService.getUserProfile(username);
        model.addAttribute("profile", profile);
        return "user/profile";
    }

    @GetMapping("/user/profile/edit")
    public String editProfile(Authentication authentication, Model model) {
        String username = authentication.getName();
        UserProfile profile = userService.getUserProfile(username);
        if (profile.getUtilisateur() == null) profile.setUtilisateur(new Utilisateur());
        if (profile.getAdresse() == null) profile.setAdresse(new Adresse());
        model.addAttribute("profile", profile);
        return "user/edit";
    }

    @PostMapping("/user/profile/save")
    public String saveProfile(@ModelAttribute("profile") UserProfile profile, Authentication authentication) {
        // ensure pseudo equals authenticated user
        String username = authentication.getName();
        profile.getUtilisateur().setPseudo(username);
        // set address id if existing
        if (profile.getAdresse() != null && profile.getAdresse().getNoAdresse() == 0) {
            // if no adresse id provided, keep existing one
            // load existing
            UserProfile existing = userService.getUserProfile(username);
            if (existing.getAdresse() != null) profile.getAdresse().setNoAdresse(existing.getAdresse().getNoAdresse());
        }

        userService.updateUserProfile(profile.getUtilisateur(), profile.getAdresse());
        return "redirect:/user/profile";
    }
}
