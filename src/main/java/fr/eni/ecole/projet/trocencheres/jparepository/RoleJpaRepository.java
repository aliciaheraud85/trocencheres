package fr.eni.ecole.projet.trocencheres.jparepository;

import fr.eni.ecole.projet.trocencheres.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpaRepository extends JpaRepository<Role, String> {
}
