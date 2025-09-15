package fr.eni.ecole.projet.trocencheres.jparepository;

import fr.eni.ecole.projet.trocencheres.model.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdresseJpaRepository extends JpaRepository<Adresse, Integer> {
}
