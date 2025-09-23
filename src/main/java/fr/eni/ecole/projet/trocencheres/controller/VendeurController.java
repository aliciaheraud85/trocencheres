package fr.eni.ecole.projet.trocencheres.controller;

import fr.eni.ecole.projet.trocencheres.dto.SellerProfil;
import fr.eni.ecole.projet.trocencheres.service.VendeurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VendeurController {

    private final VendeurService vendeurService;

    public VendeurController(VendeurService vendeurService) {
        this.vendeurService = vendeurService;
    }

    @GetMapping("/vendeur/profil")
    public String profil(Model model) {
        SellerProfil profile = vendeurService.getFirstSellerProfil();
        model.addAttribute("profile", profile);
        return "user/seller";
    }
}
