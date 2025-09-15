package fr.eni.ecole.projet.trocencheres.model;

public class Adresse {
    private int noAdresse;
    private String rue;
    private String codePostal;
    private String ville;
    private boolean adresseEni;

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
}
