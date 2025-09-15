package fr.eni.ecole.projet.trocencheres.jparepository;

import fr.eni.ecole.projet.trocencheres.model.ArticleAVendre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleAVendreJpaRepository extends JpaRepository<ArticleAVendre, int> {
}
