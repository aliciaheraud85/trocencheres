package fr.eni.ecole.projet.trocencheres.bo;

public class Adresse {
    private int noAdresse;
    private String rue;
    private String codePostal;
    private String ville;
    private boolean adresseEni;
    
    public Adresse(int noAdresse, String rue, String codePostal, String ville, boolean adresseEni) {
        this.noAdresse = noAdresse;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.adresseEni = adresseEni;
    }

    // getters and setters
    public int getNoAdresse() { return noAdresse; }
    public void setNoAdresse(int noAdresse) { this.noAdresse = noAdresse; }
    public String getRue() { return rue; }
    public void setRue(String rue) { this.rue = rue; }
    public String getCodePostal() { return codePostal; }
    public void setCodePostal(String codePostal) { this.codePostal = codePostal; }
    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }
    public boolean isAdresseEni() { return adresseEni; }
    public void setAdresseEni(boolean adresseEni) { this.adresseEni = adresseEni; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Adresse adresse = (Adresse) obj;
        return noAdresse == adresse.noAdresse &&
               adresseEni == adresse.adresseEni &&
               rue.equals(adresse.rue) &&
               codePostal.equals(adresse.codePostal) &&
               ville.equals(adresse.ville);
    }

    @Override
    public String toString() {
        return "Adresse{" +
               "noAdresse=" + noAdresse +
               ", rue='" + rue + '\'' +
               ", codePostal='" + codePostal + '\'' +
               ", ville='" + ville + '\'' +
               ", adresseEni=" + adresseEni +
               '}';
    }
}
