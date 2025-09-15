package fr.eni.ecole.projet.trocencheres.bo;

import java.time.LocalDateTime;

public class Enchere {
    private String idUtilisateur;
    private int noArticle;
    private int montantEnchere;
    private LocalDateTime dateEnchere;

    public Enchere(String idUtilisateur, int noArticle, int montantEnchere, LocalDateTime dateEnchere) {
        this.idUtilisateur = idUtilisateur;
        this.noArticle = noArticle;
        this.montantEnchere = montantEnchere;
        this.dateEnchere = dateEnchere;
    }

    public String getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(String idUtilisateur) { this.idUtilisateur = idUtilisateur; }
    public int getNoArticle() { return noArticle; }
    public void setNoArticle(int noArticle) { this.noArticle = noArticle; }
    public int getMontantEnchere() { return montantEnchere; }
    public void setMontantEnchere(int montantEnchere) { this.montantEnchere = montantEnchere; }
    public LocalDateTime getDateEnchere() { return dateEnchere; }
    public void setDateEnchere(LocalDateTime dateEnchere) { this.dateEnchere = dateEnchere; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Enchere enchere = (Enchere) obj;
        return noArticle == enchere.noArticle &&
               montantEnchere == enchere.montantEnchere &&
               idUtilisateur.equals(enchere.idUtilisateur) &&
               dateEnchere.equals(enchere.dateEnchere);
    }

    @Override
    public String toString() {
        return "Enchere{" +
               "idUtilisateur='" + idUtilisateur + '\'' +
               ", noArticle=" + noArticle +
               ", montantEnchere=" + montantEnchere +
               ", dateEnchere=" + dateEnchere +
               '}';
    }
}
