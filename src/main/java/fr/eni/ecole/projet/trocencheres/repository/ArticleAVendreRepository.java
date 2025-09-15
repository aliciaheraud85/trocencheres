package fr.eni.ecole.projet.trocencheres.repository;

import fr.eni.ecole.projet.trocencheres.model.ArticleAVendre;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleAVendreRepository {

    private final JdbcTemplate jdbc;

    public ArticleAVendreRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<ArticleAVendre> MAPPER = new RowMapper<ArticleAVendre>() {
        @Override
        public ArticleAVendre mapRow(ResultSet rs, int rowNum) throws SQLException {
            ArticleAVendre a = new ArticleAVendre();
            a.setNoArticle(rs.getInt("no_article"));
            a.setNomArticle(rs.getString("nom_article"));
            a.setDescription(rs.getString("description"));
            a.setPhoto((int) rs.getObject("photo"));
            a.setDateDebutEncheres(rs.getDate("date_debut_encheres") != null ? rs.getDate("date_debut_encheres").toLocalDate() : null);
            a.setDateFinEncheres(rs.getDate("date_fin_encheres") != null ? rs.getDate("date_fin_encheres").toLocalDate() : null);
            a.setStatutEnchere(rs.getInt("statut_enchere"));
            a.setPrixInitial(rs.getInt("prix_initial"));
            a.setPrixVente((int) rs.getObject("prix_vente"));
            a.setIdUtilisateur(rs.getString("id_utilisateur"));
            a.setNoCategorie(rs.getInt("no_categorie"));
            a.setNoAdresseRetrait(rs.getInt("no_adresse_retrait"));
            return a;
        }
    };

    public Optional<ArticleAVendre> findById(int id) {
        List<ArticleAVendre> list = jdbc.query("select * from ARTICLES_A_VENDRE where no_article = ?", MAPPER, id);
        return list.stream().findFirst();
    }

    public List<ArticleAVendre> findAll() {
        return jdbc.query("select * from ARTICLES_A_VENDRE", MAPPER);
    }
}
