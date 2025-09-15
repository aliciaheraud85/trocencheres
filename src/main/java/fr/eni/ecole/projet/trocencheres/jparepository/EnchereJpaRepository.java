package fr.eni.ecole.projet.trocencheres.jparepository;

import fr.eni.ecole.projet.trocencheres.model.Enchere;
import fr.eni.ecole.projet.trocencheres.model.EnchereId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnchereJpaRepository extends JpaRepository<Enchere, EnchereId> {
}
