package fr.eni.ecole.projet.trocencheres.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CATEGORIES")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no_categorie")
    private Integer noCategorie;

    @Column(name = "libelle")
    private String libelle;

    public Integer getNoCategorie() { return noCategorie; }
    public void setNoCategorie(Integer noCategorie) { this.noCategorie = noCategorie; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
}
