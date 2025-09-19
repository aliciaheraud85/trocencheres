package fr.eni.ecole.projet.trocencheres.repository;

import fr.eni.ecole.projet.trocencheres.bo.Enchere;
import fr.eni.ecole.projet.trocencheres.bo.Utilisateur;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EnchereRepository {

    private final JdbcTemplate jdbc;

    public EnchereRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<Enchere> MAPPER = new RowMapper<Enchere>() {
        @Override
        public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
            Enchere e = new Enchere();
            e.setIdUtilisateur(rs.getString("id_utilisateur"));
            e.setNoArticle(rs.getInt("no_article"));
            e.setMontantEnchere(rs.getInt("montant_enchere"));
            e.setDateEnchere(rs.getTimestamp("date_enchere") != null ? rs.getTimestamp("date_enchere").toLocalDateTime() : null);
            return e;
        }
    };

    public List<Enchere> findByArticleId(int articleId) {
        return jdbc.query("select * from ENCHERES where no_article = ? order by date_enchere desc", MAPPER, articleId);
    }

    public List<Enchere> findAll() {
        return jdbc.query("select * from ENCHERES", MAPPER);
    }

    public int createEnchere(Enchere bid) {
        String queryString = "INSERT INTO ENCHERES(id_utilisateur, no_article, montant_enchere, date_enchere) VALUES (?, ?, ?, ?)";
        return jdbc.update(queryString, bid.getIdUtilisateur(), bid.getNoArticle(), bid.getMontantEnchere(), bid.getDateEnchere());
    }

}
