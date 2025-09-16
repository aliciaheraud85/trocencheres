package fr.eni.ecole.projet.trocencheres.repository;

import fr.eni.ecole.projet.trocencheres.bo.Categorie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class CategorieRepository {

    private final JdbcTemplate jdbc;

    public CategorieRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<Categorie> MAPPER = new RowMapper<Categorie>() {
        @Override
        public Categorie mapRow(ResultSet rs, int rowNum) throws SQLException {
            Categorie c = new Categorie();
            c.setNoCategorie(rs.getInt("no_categorie"));
            c.setLibelle(rs.getString("libelle"));
            return c;
        }
    };

    public Optional<Categorie> findById(int id) {
        List<Categorie> list = jdbc.query("select * from CATEGORIES where no_categorie = ?", MAPPER, id);
        return list.stream().findFirst();
    }

    public List<Categorie> findAll() {
        return jdbc.query("select * from CATEGORIES", MAPPER);
    }
}
