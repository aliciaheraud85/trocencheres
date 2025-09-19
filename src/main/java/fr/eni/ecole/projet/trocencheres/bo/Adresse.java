package fr.eni.ecole.projet.trocencheres.bo;

import org.springframework.dao.DuplicateKeyException;

public class Adresse {
    public int noAdresse;
    private String rue;
    private String codePostal;
    private String ville;
    private boolean adresseEni;

    public Adresse() {}
    
    public Adresse(String rue, String codePostal, String ville, boolean adresseEni) {
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.adresseEni = adresseEni;
    }

    // getters and setters
    public String getRue() { return rue; }
    public void setRue(String rue) { this.rue = rue; }
    public String getCodePostal() { return codePostal; }
    public void setCodePostal(String codePostal) { this.codePostal = codePostal; }
    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }
    public boolean isAdresseEni() { return adresseEni; }
    public void setAdresseEni(boolean adresseEni) { this.adresseEni = adresseEni; }
    public int getNoAdresse() {return noAdresse;}
    public void setNoAdresse(int noAdresse) {this.noAdresse = noAdresse;}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Adresse adresse = (Adresse) obj;
        return adresseEni == adresse.adresseEni &&
               rue.equals(adresse.rue) &&
               codePostal.equals(adresse.codePostal) &&
               ville.equals(adresse.ville);
    }

    @Override
    public String toString() throws DuplicateKeyException {
        return "Adresse{" +
               ", rue='" + rue + '\'' +
               ", codePostal='" + codePostal + '\'' +
               ", ville='" + ville + '\'' +
               ", adresseEni=" + adresseEni +
               '}';
    }
}
