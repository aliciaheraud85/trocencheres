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
     * returns the first user which sells article in the page /vendeur/profil (take only the first for now just to have an overview)
     */
    public SellerProfil getFirstSellerProfil() {
        List<ArticleAVendre> articles = articleRepository.findAll();
        if (articles.isEmpty()) {
            return new SellerProfil();
        }

        ArticleAVendre a = articles.get(0);
        Utilisateur seller = utilisateurRepository.findByPseudo(a.getIdUtilisateur()).orElse(null);
        return new SellerProfil(seller, a);
    }
}
