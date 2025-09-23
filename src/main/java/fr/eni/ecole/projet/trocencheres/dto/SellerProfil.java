package fr.eni.ecole.projet.trocencheres.dto;

import fr.eni.ecole.projet.trocencheres.bo.ArticleAVendre;
import fr.eni.ecole.projet.trocencheres.bo.Utilisateur;

import java.util.List;

public class SellerProfil {
    private Utilisateur seller;
    private List<ArticleAVendre> articles;

    public SellerProfil() {}
    public SellerProfil(Utilisateur vendeur, List<ArticleAVendre> articles) {
        this.seller = vendeur;
        this.articles = articles;
    }

    public Utilisateur getVendeur() { return seller; }
    public void setVendeur(Utilisateur vendeur) { this.seller = vendeur; }
    public List<ArticleAVendre> getArticles() { return articles; }
    public void setArticles(List<ArticleAVendre> articles) { this.articles = articles; }
}
