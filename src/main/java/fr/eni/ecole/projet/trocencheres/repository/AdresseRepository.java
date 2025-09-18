package fr.eni.ecole.projet.trocencheres.repository;

import fr.eni.ecole.projet.trocencheres.bo.Adresse;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class AdresseRepository {

    private final JdbcTemplate jdbc;

    public AdresseRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<Adresse> MAPPER = new RowMapper<Adresse>() {
        @Override
        public Adresse mapRow(ResultSet rs, int rowNum) throws SQLException {
            Adresse a = new Adresse();
            a.setRue(rs.getString("rue"));
            a.setCodePostal(rs.getString("code_postal"));
            a.setVille(rs.getString("ville"));
            a.setAdresseEni(rs.getBoolean("adresse_eni"));
            return a;
        }
    };

    public Optional<Adresse> findById(int id) {
        List<Adresse> list = jdbc.query("select * from ADRESSES where no_adresse = ?", MAPPER, id);
        return list.stream().findFirst();
    }

    public List<Adresse> findAll() {
        return jdbc.query("select * from ADRESSES", MAPPER);
    }

    public int addAddress(Adresse address) {
        String queryString = "INSERT INTO ADRESSES(rue, code_postal, ville, adresse_eni) VALUES(?, ?, ?, ?)";
        return jdbc.update(queryString, address.getRue(), address.getCodePostal(), address.getVille(), address.isAdresseEni());
    }
}
