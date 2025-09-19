package fr.eni.ecole.projet.trocencheres.repository;

import fr.eni.ecole.projet.trocencheres.bo.ArticleAVendre;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            // Check for null before casting
            Object photoObj = rs.getObject("photo");
            a.setPhoto(photoObj != null ? (int) photoObj : 0); // or null if photo is Integer

            a.setDateDebutEncheres(rs.getDate("date_debut_encheres") != null
                    ? rs.getDate("date_debut_encheres").toLocalDate()
                    : null);
            a.setDateFinEncheres(rs.getDate("date_fin_encheres") != null
                    ? rs.getDate("date_fin_encheres").toLocalDate()
                    : null);
            a.setStatutEnchere(rs.getInt("statut_enchere"));
            a.setPrixInitial(rs.getInt("prix_initial"));

            Object prixVenteObj = rs.getObject("prix_vente");
            a.setPrixVente(prixVenteObj != null ? (int) prixVenteObj : 0); // or null if Integer

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

    public long createArticleAVendre(ArticleAVendre a) {
        String sql = "INSERT INTO ARTICLES_A_VENDRE " +
                "(nom_article, description, date_debut_encheres, datee_fin_encheres, statut_enchere, prix_initial, prix_vente, id_utilisateur, no_categorie, no_adresse_retrait)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, a.getNomArticle());
            ps.setString(2, a.getDescription());
            ps.setObject(3, a.getDateDebutEncheres() != null ? java.sql.Date.valueOf(a.getDateDebutEncheres()) : null);
            ps.setObject(4, a.getDateFinEncheres() != null ? java.sql.Date.valueOf(a.getDateFinEncheres()) : null);
            ps.setInt(5, a.getStatutEnchere());
            ps.setInt(6, a.getPrixInitial());
            ps.setInt(7, a.getPrixVente());
            ps.setString(8, a.getIdUtilisateur());
            ps.setInt(8, a.getNoCategorie());
            ps.setInt(9, a.getNoAdresseRetrait());

            return ps;

        }, keyHolder);

        Number generatedId =  keyHolder.getKey();
        return generatedId != null ? generatedId.intValue() : -1;
    }

    public List<ArticleAVendre> findByCategory(int categoryId) {
        return jdbc.query("select * from ARTICLES_A_VENDRE where no_categorie = ?", MAPPER, categoryId);
    }

    public List<ArticleAVendre> findByCategoryAndName(int categoryId, String namePattern) {
        return jdbc.query("select * from ARTICLES_A_VENDRE where no_categorie = ? and nom_article like ?", MAPPER, categoryId, namePattern);
    }

    public List<ArticleAVendre> findByName(String namePattern) {
        return jdbc.query("select * from ARTICLES_A_VENDRE where nom_article like ?", MAPPER, namePattern);
    }

    public List<ArticleAVendre> findByParticipant(String username) {
        String sql = "select distinct a.* from ARTICLES_A_VENDRE a " +
                "join ENCHERES e on a.no_article = e.no_article " +
                "where e.id_utilisateur = ?";
        return jdbc.query(sql, MAPPER, username);
    }

    public List<ArticleAVendre> findWonByUser(String username) {
        // Won = auction finished (statut_enchere = 2) and highest bidder = username
        String sql = "select a.* from ARTICLES_A_VENDRE a " +
                "join ENCHERES e on a.no_article = e.no_article " +
                "where a.statut_enchere = 2 and e.id_utilisateur = ? and e.montant_enchere = (select max(montant_enchere) from ENCHERES where no_article = a.no_article)";
        return jdbc.query(sql, MAPPER, username);
    }

    public List<ArticleAVendre> findBySellerAndStatus(String sellerId, Integer statutEnchere) {
        if (statutEnchere == null) {
            return jdbc.query("select * from ARTICLES_A_VENDRE where id_utilisateur = ?", MAPPER, sellerId);
        }
        return jdbc.query("select * from ARTICLES_A_VENDRE where id_utilisateur = ? and statut_enchere = ?", MAPPER, sellerId, statutEnchere);
    }
}
