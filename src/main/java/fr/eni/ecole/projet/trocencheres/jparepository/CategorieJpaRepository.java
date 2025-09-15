package fr.eni.ecole.projet.trocencheres.jparepository;

import fr.eni.ecole.projet.trocencheres.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieJpaRepository extends JpaRepository<Categorie, int> {
}
