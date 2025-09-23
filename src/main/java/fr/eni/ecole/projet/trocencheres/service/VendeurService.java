package fr.eni.ecole.projet.trocencheres.service;

import fr.eni.ecole.projet.trocencheres.bo.ArticleAVendre;
import fr.eni.ecole.projet.trocencheres.bo.Utilisateur;
import fr.eni.ecole.projet.trocencheres.dto.SellerProfil;
import fr.eni.ecole.projet.trocencheres.repository.ArticleAVendreRepository;
import fr.eni.ecole.projet.trocencheres.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendeurService {

    private final UtilisateurRepository utilisateurRepository;
    private final ArticleAVendreRepository articleRepository;

    public VendeurService(UtilisateurRepository utilisateurRepository, ArticleAVendreRepository articleRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.articleRepository = articleRepository;
    }

    /**
     * returns the user which sells article in the page /vendeur/profil
     */
    public SellerProfil getSellerProfil(String pseudo) {
        Utilisateur seller = utilisateurRepository.findByPseudo(pseudo)
                .orElse(null);
        if (seller == null) {
            return new SellerProfil(); // or throw exception
        }

        // 2. Fetch all articles of this seller
        List<ArticleAVendre> articles = articleRepository.findBySellerId(seller.getPseudo());

        // 3. Return profile
        return new SellerProfil(seller, articles);
    }

}
