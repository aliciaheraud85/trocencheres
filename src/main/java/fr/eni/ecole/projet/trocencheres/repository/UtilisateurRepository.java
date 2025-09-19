package fr.eni.ecole.projet.trocencheres.repository;

import fr.eni.ecole.projet.trocencheres.bo.Adresse;
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

    public List<Utilisateur> findAll() {
        return jdbc.query("select * from UTILISATEURS", MAPPER);
    }

    public int addUser(Utilisateur user) {
        String queryString = "INSERT INTO UTILISATEURS(pseudo, nom, prenom, email, telephone, mot_de_passe, credit, administrateur, no_adresse) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbc.update(queryString, user.getPseudo(), user.getNom(), user.getPrenom(), user.getEmail(), user.getTelephone(), user.getMotDePasse(), user.getCredit(), user.isAdministrateur(), user.getNoAdresse());
    }

    public int updateUtilisateur(Utilisateur u) {
        return jdbc.update("update UTILISATEURS set nom = ?, prenom = ?, email = ?, telephone = ?, mot_de_passe = ?, no_adresse = ? where pseudo = ?",
                u.getNom(), u.getPrenom(), u.getEmail(), u.getTelephone(), u.getMotDePasse(), u.getNoAdresse(), u.getPseudo());
    }

    public Optional<Utilisateur> findLastBidder(int articleId) {
        return jdbc.query("select TOP 1 * from ENCHERES left join UTILISATEURS as u on u.pseudo = id_utilisateur where no_article = 1 order by date_enchere desc", MAPPER, articleId).stream().findFirst();
    }
}
