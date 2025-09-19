package fr.eni.ecole.projet.trocencheres.dto;

import fr.eni.ecole.projet.trocencheres.bo.Adresse;
import fr.eni.ecole.projet.trocencheres.bo.Utilisateur;

public class UserProfile {
    private Utilisateur utilisateur;
    private Adresse adresse;

    public UserProfile() {}
    public UserProfile(Utilisateur utilisateur, Adresse adresse) {
        this.utilisateur = utilisateur;
        this.adresse = adresse;
    }

    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }
    public Adresse getAdresse() { return adresse; }
    public void setAdresse(Adresse adresse) { this.adresse = adresse; }
}
