package fr.eni.ecole.projet.trocencheres.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "UTILISATEURS")
public class Utilisateur {
    @Id
    @Column(name = "pseudo")
    private String pseudo;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "email")
    private String email;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "mot_de_passe")
    private String motDePasse;

    @Column(name = "credit")
    private Integer credit;

    @Column(name = "administrateur")
    private boolean administrateur;

    @Column(name = "no_adresse")
    private Integer noAdresse;

    @OneToMany(mappedBy = "vendeur", fetch = FetchType.LAZY)
    private List<ArticleAVendre> articles;

    // getters/setters
    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    public Integer getCredit() { return credit; }
    public void setCredit(Integer credit) { this.credit = credit; }
    public boolean isAdministrateur() { return administrateur; }
    public void setAdministrateur(boolean administrateur) { this.administrateur = administrateur; }
    public Integer getNoAdresse() { return noAdresse; }
    public void setNoAdresse(Integer noAdresse) { this.noAdresse = noAdresse; }
    public List<ArticleAVendre> getArticles() { return articles; }
    public void setArticles(List<ArticleAVendre> articles) { this.articles = articles; }
}
