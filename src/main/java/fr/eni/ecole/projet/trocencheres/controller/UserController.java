package fr.eni.ecole.projet.trocencheres.controller;

import fr.eni.ecole.projet.trocencheres.bo.Adresse;
import fr.eni.ecole.projet.trocencheres.bo.Utilisateur;
import fr.eni.ecole.projet.trocencheres.dto.UserProfile;
import java.security.Principal;
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
    public String profile(Principal principal, Model model) {
        String username = principal != null ? principal.getName() : null;
        UserProfile profile = null;
        if (username != null) {
            profile = userService.getUserProfile(username);
        }
        model.addAttribute("profile", profile);
        return "user/profile";
    }

    @GetMapping("/user/profile/edit")
    public String editProfile(Principal principal, Model model) {
        String username = principal != null ? principal.getName() : null;
        if (username == null) return "redirect:/login";
        UserProfile profile = userService.getUserProfile(username);
        if (profile.getUtilisateur() == null) profile.setUtilisateur(new Utilisateur());
        if (profile.getAdresse() == null) profile.setAdresse(new Adresse());
        model.addAttribute("profile", profile);
        return "user/edit";
    }

    @PostMapping("/user/profile/save")
    public String saveProfile(@ModelAttribute("profile") UserProfile profile, Principal principal) {
        String username = principal != null ? principal.getName() : null;
        if (username == null) return "redirect:/login";

        // ensure pseudo equals authenticated user
        if (profile.getUtilisateur() == null) {
            profile.setUtilisateur(new Utilisateur());
        }
        profile.getUtilisateur().setPseudo(username);

        // set address id if missing, preserve existing if necessary
        if (profile.getAdresse() != null && profile.getAdresse().getNoAdresse() == 0) {
            UserProfile existing = userService.getUserProfile(username);
            if (existing.getAdresse() != null) profile.getAdresse().setNoAdresse(existing.getAdresse().getNoAdresse());
        }

        boolean updated = userService.updateUserProfile(profile.getUtilisateur(), profile.getAdresse());
        if (updated) return "redirect:/user/profile";
        return "redirect:/user/profile/edit";
    }
}
