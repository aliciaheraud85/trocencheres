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

    public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, String motDePasse, int credit, boolean administrateur, int noAdresse) {
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.motDePasse = motDePasse;
        this.credit = credit;
        this.administrateur = administrateur;
        this.noAdresse = noAdresse;
    }

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
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Utilisateur that = (Utilisateur) obj;
        return credit == that.credit &&
               administrateur == that.administrateur &&
               noAdresse == that.noAdresse &&
               pseudo.equals(that.pseudo) &&
               nom.equals(that.nom) &&
               prenom.equals(that.prenom) &&
               email.equals(that.email) &&
               telephone.equals(that.telephone) &&
               motDePasse.equals(that.motDePasse);
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
               "pseudo='" + pseudo + '\'' +
               ", nom='" + nom + '\'' +
               ", prenom='" + prenom + '\'' +
               ", email='" + email + '\'' +
               ", telephone='" + telephone + '\'' +
               ", motDePasse='" + motDePasse + '\'' +
               ", credit=" + credit +
               ", administrateur=" + administrateur +
               ", noAdresse=" + noAdresse +
               '}';
    }
}
