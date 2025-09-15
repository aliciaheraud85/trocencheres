package fr.eni.ecole.projet.trocencheres.model;

public class Utilisateur {
    private String pseudo;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String motDePasse;
    private int credit;
    private boolean administrateur;
    private int noAdresse;

    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    public int getCredit() { return credit; }
    public void setCredit(int credit) { this.credit = credit; }
    public boolean isAdministrateur() { return administrateur; }
    public void setAdministrateur(boolean administrateur) { this.administrateur = administrateur; }
    public int getNoAdresse() { return noAdresse; }
    public void setNoAdresse(int noAdresse) { this.noAdresse = noAdresse; }
    
}
