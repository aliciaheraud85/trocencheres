package fr.eni.ecole.projet.trocencheres.bo;

public class Categorie {
    private int noCategorie;
    private String libelle;

    public Categorie() {}

    public int getNoCategorie() { return noCategorie; }
    public void setNoCategorie(int noCategorie) { this.noCategorie = noCategorie; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Categorie categorie = (Categorie) obj;
        return noCategorie == categorie.noCategorie &&
               libelle.equals(categorie.libelle);
    }

    @Override
    public String toString() {
        return "Categorie{" +
               "noCategorie=" + noCategorie +
               ", libelle='" + libelle + '\'' +
               '}';
    }
}
