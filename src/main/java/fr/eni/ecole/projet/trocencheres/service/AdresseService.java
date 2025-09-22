package fr.eni.ecole.projet.trocencheres.service;

import fr.eni.ecole.projet.trocencheres.bo.Adresse;
import fr.eni.ecole.projet.trocencheres.bo.ArticleAVendre;
import fr.eni.ecole.projet.trocencheres.bo.Utilisateur;
import fr.eni.ecole.projet.trocencheres.dto.SellerProfil;
import fr.eni.ecole.projet.trocencheres.repository.AdresseRepository;
import fr.eni.ecole.projet.trocencheres.repository.ArticleAVendreRepository;
import fr.eni.ecole.projet.trocencheres.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdresseService {

    private final AdresseRepository adresseRepository;

    public AdresseService(AdresseRepository adresseRepository) {
        this.adresseRepository = adresseRepository;
    }


    public Adresse getAdresse(int id) {
        return adresseRepository.findById(id).isPresent() ? adresseRepository.findById(id).get() : null;
    }
}
