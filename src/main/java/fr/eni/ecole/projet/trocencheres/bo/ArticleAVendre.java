package fr.eni.ecole.projet.trocencheres.bo;

import java.time.LocalDate;

public class ArticleAVendre {
    private int noArticle;
    private String nomArticle;
    private String description;
    private int photo;
    private LocalDate dateDebutEncheres;
    private LocalDate dateFinEncheres;
    private int statutEnchere;
    private int prixInitial;
    private int prixVente;
    private String idUtilisateur;
    private int noCategorie;
    private int noAdresseRetrait;
    private Enchere enchere;

    public ArticleAVendre() {}

    public ArticleAVendre(int noArticle, String nomArticle, String description, int photo, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int statutEnchere, int prixInitial, int prixVente, String idUtilisateur, int noCategorie, int noAdresseRetrait) {
        this.noArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.photo = photo;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.statutEnchere = 0;
        this.prixInitial = 0;
        this.prixVente = 0;
        this.idUtilisateur = idUtilisateur;
        this.noCategorie = 1;
        this.noAdresseRetrait = 1;
    }

    // getters/setters
    public int getNoArticle() { return noArticle; }
    public void setNoArticle(int noArticle) { this.noArticle = noArticle; }
    public String getNomArticle() { return nomArticle; }
    public void setNomArticle(String nomArticle) { this.nomArticle = nomArticle; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getPhoto() { return photo; }
    public void setPhoto(int photo) { this.photo = photo; }
    public LocalDate getDateDebutEncheres() { return dateDebutEncheres; }
    public void setDateDebutEncheres(LocalDate dateDebutEncheres) { this.dateDebutEncheres = dateDebutEncheres; }
    public LocalDate getDateFinEncheres() { return dateFinEncheres; }
    public void setDateFinEncheres(LocalDate dateFinEncheres) { this.dateFinEncheres = dateFinEncheres; }
    public int getStatutEnchere() { return statutEnchere; }
    public void setStatutEnchere(int statutEnchere) { this.statutEnchere = statutEnchere; }
    public int getPrixInitial() { return prixInitial; }
    public void setPrixInitial(int prixInitial) { this.prixInitial = prixInitial; }
    public int getPrixVente() { return prixVente; }
    public void setPrixVente(int prixVente) { this.prixVente = prixVente; }
    public String getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(String idUtilisateur) { this.idUtilisateur = idUtilisateur; }
    public int getNoCategorie() { return noCategorie; }
    public void setNoCategorie(int noCategorie) { this.noCategorie = noCategorie; }
    public int getNoAdresseRetrait() { return noAdresseRetrait; }
    public void setNoAdresseRetrait(int noAdresseRetrait) { this.noAdresseRetrait = noAdresseRetrait; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ArticleAVendre article = (ArticleAVendre) obj;
        return noArticle == article.noArticle &&
               photo == article.photo &&
               statutEnchere == article.statutEnchere &&
               prixInitial == article.prixInitial &&
               prixVente == article.prixVente &&
               noCategorie == article.noCategorie &&
               noAdresseRetrait == article.noAdresseRetrait &&
               nomArticle.equals(article.nomArticle) &&
               description.equals(article.description) &&
               dateDebutEncheres.equals(article.dateDebutEncheres) &&
               dateFinEncheres.equals(article.dateFinEncheres) &&
               idUtilisateur.equals(article.idUtilisateur);
    }

    @Override
    public String toString() {
        return "ArticleAVendre{" +
               "noArticle=" + noArticle +
               ", nomArticle='" + nomArticle + '\'' +
               ", description='" + description + '\'' +
               ", photo=" + photo +
               ", dateDebutEncheres=" + dateDebutEncheres +
               ", dateFinEncheres=" + dateFinEncheres +
               ", statutEnchere=" + statutEnchere +
               ", prixInitial=" + prixInitial +
               ", prixVente=" + prixVente +
               ", idUtilisateur='" + idUtilisateur + '\'' +
               ", noCategorie=" + noCategorie +
               ", noAdresseRetrait=" + noAdresseRetrait +
               '}';
    }
}
