package fr.eni.ecole.projet.trocencheres.dto;

import fr.eni.ecole.projet.trocencheres.bo.ArticleAVendre;
import fr.eni.ecole.projet.trocencheres.bo.Utilisateur;

public class SellerProfil {
    private Utilisateur seller;
    private ArticleAVendre article;

    public SellerProfil() {}
    public SellerProfil(Utilisateur vendeur, ArticleAVendre article) {
        this.seller = vendeur;
        this.article = article;
    }

    public Utilisateur getVendeur() { return seller; }
    public void setVendeur(Utilisateur vendeur) { this.seller = vendeur; }
    public ArticleAVendre getArticle() { return article; }
    public void setArticle(ArticleAVendre article) { this.article = article; }
}
