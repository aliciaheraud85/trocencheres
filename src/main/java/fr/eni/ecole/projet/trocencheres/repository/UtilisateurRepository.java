package fr.eni.ecole.projet.trocencheres.repository;

import fr.eni.ecole.projet.trocencheres.bo.Utilisateur;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UtilisateurRepository {

    private final JdbcTemplate jdbc;

    public UtilisateurRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<Utilisateur> MAPPER = new RowMapper<Utilisateur>() {
        @Override
        public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
            Utilisateur u = new Utilisateur();
            u.setPseudo(rs.getString("pseudo"));
            u.setNom(rs.getString("nom"));
            u.setPrenom(rs.getString("prenom"));
            u.setEmail(rs.getString("email"));
            u.setTelephone(rs.getString("telephone"));
            u.setMotDePasse(rs.getString("mot_de_passe"));
            u.setCredit(rs.getInt("credit"));
            u.setAdministrateur(rs.getBoolean("administrateur"));
            u.setNoAdresse(rs.getInt("no_adresse"));
            return u;
        }
    };

    public Optional<Utilisateur> findByPseudo(String pseudo) {
        List<Utilisateur> list = jdbc.query("select * from UTILISATEURS where pseudo = ?", MAPPER, pseudo);
        return list.stream().findFirst();
    }

    public String getPasswordByPseudo(String pseudo) {
        List<Utilisateur> list = jdbc.query("select password from UTILISATEURS where pseudo = ?", MAPPER, pseudo);
        Optional<Utilisateur> response = list.stream().findFirst();
        if (response.isPresent()){
            return response.get().getMotDePasse();
        }
        else {
            return null;
        }
    }

    public List<Utilisateur> findAll() {
        return jdbc.query("select * from UTILISATEURS", MAPPER);
    }
}
