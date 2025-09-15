package fr.eni.ecole.projet.trocencheres.model;

import java.time.LocalDateTime;

public class Enchere {
    private String idUtilisateur;
    private int noArticle;
    private int montantEnchere;
    private LocalDateTime dateEnchere;

    public String getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(String idUtilisateur) { this.idUtilisateur = idUtilisateur; }
    public int getNoArticle() { return noArticle; }
    public void setNoArticle(int noArticle) { this.noArticle = noArticle; }
    public int getMontantEnchere() { return montantEnchere; }
    public void setMontantEnchere(int montantEnchere) { this.montantEnchere = montantEnchere; }
    public LocalDateTime getDateEnchere() { return dateEnchere; }
    public void setDateEnchere(LocalDateTime dateEnchere) { this.dateEnchere = dateEnchere; }
}
