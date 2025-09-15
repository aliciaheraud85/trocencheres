package fr.eni.ecole.projet.trocencheres.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ADRESSES")
public class Adresse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no_adresse")
    private Integer noAdresse;

    @Column(name = "rue")
    private String rue;

    @Column(name = "code_postal")
    private String codePostal;

    @Column(name = "ville")
    private String ville;

    @Column(name = "adresse_eni")
    private boolean adresseEni;

    public Integer getNoAdresse() { return noAdresse; }
    public void setNoAdresse(Integer noAdresse) { this.noAdresse = noAdresse; }
    public String getRue() { return rue; }
    public void setRue(String rue) { this.rue = rue; }
    public String getCodePostal() { return codePostal; }
    public void setCodePostal(String codePostal) { this.codePostal = codePostal; }
    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }
    public boolean isAdresseEni() { return adresseEni; }
    public void setAdresseEni(boolean adresseEni) { this.adresseEni = adresseEni; }
}
