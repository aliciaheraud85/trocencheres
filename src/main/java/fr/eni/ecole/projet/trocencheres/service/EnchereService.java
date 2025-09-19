package fr.eni.ecole.projet.trocencheres.service;


import fr.eni.ecole.projet.trocencheres.bo.Enchere;
import fr.eni.ecole.projet.trocencheres.bo.Utilisateur;
import fr.eni.ecole.projet.trocencheres.repository.EnchereRepository;
import fr.eni.ecole.projet.trocencheres.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class EnchereService {

    @Autowired
    EnchereRepository enchereRepository;

    public void createEnchere(Utilisateur bidder, int id, int amount) throws SQLException {
        Enchere bid = new Enchere(bidder.getPseudo(), id, amount, LocalDateTime.now());
        int success = enchereRepository.createEnchere(bid);
        if (success == 0) {
            throw new SQLException("database enchere insert failed");
        }
    }
}