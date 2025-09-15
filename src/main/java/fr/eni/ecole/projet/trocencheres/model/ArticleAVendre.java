package fr.eni.ecole.projet.trocencheres.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ARTICLES_A_VENDRE")
public class ArticleAVendre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no_article")
    private Integer noArticle;

    @Column(name = "nom_article")
    private String nomArticle;

    @Column(name = "description")
    private String description;

    @Column(name = "photo")
    private Integer photo;

    @Column(name = "date_debut_encheres")
    private LocalDate dateDebutEncheres;

    @Column(name = "date_fin_encheres")
    private LocalDate dateFinEncheres;

    @Column(name = "statut_enchere")
    private Integer statutEnchere;

    @Column(name = "prix_initial")
    private Integer prixInitial;

    @Column(name = "prix_vente")
    private Integer prixVente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur vendeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "no_categorie")
    private Categorie categorie;

    @Column(name = "no_adresse_retrait")
    private Integer noAdresseRetrait;

    // getters/setters
    public Integer getNoArticle() { return noArticle; }
    public void setNoArticle(Integer noArticle) { this.noArticle = noArticle; }
    public String getNomArticle() { return nomArticle; }
    public void setNomArticle(String nomArticle) { this.nomArticle = nomArticle; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getPhoto() { return photo; }
    public void setPhoto(Integer photo) { this.photo = photo; }
    public LocalDate getDateDebutEncheres() { return dateDebutEncheres; }
    public void setDateDebutEncheres(LocalDate dateDebutEncheres) { this.dateDebutEncheres = dateDebutEncheres; }
    public LocalDate getDateFinEncheres() { return dateFinEncheres; }
    public void setDateFinEncheres(LocalDate dateFinEncheres) { this.dateFinEncheres = dateFinEncheres; }
    public Integer getStatutEnchere() { return statutEnchere; }
    public void setStatutEnchere(Integer statutEnchere) { this.statutEnchere = statutEnchere; }
    public Integer getPrixInitial() { return prixInitial; }
    public void setPrixInitial(Integer prixInitial) { this.prixInitial = prixInitial; }
    public Integer getPrixVente() { return prixVente; }
    public void setPrixVente(Integer prixVente) { this.prixVente = prixVente; }
    public Utilisateur getVendeur() { return vendeur; }
    public void setVendeur(Utilisateur vendeur) { this.vendeur = vendeur; }
    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }
    public Integer getNoAdresseRetrait() { return noAdresseRetrait; }
    public void setNoAdresseRetrait(Integer noAdresseRetrait) { this.noAdresseRetrait = noAdresseRetrait; }
}
