package fr.eni.ecole.projet.trocencheres.controller;

import fr.eni.ecole.projet.trocencheres.dto.SellerProfil;
import fr.eni.ecole.projet.trocencheres.service.VendeurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VendeurController {

    private final VendeurService vendeurService;

    public VendeurController(VendeurService vendeurService) {
        this.vendeurService = vendeurService;
    }

    @GetMapping("/vendeur/profil")
    public String profil(@RequestParam String pseudo, Model model) {
        SellerProfil profile = vendeurService.getSellerProfil(pseudo);
        model.addAttribute("profile", profile);
        model.addAttribute("pseudo", pseudo);
        return "user/seller";
    }
}
