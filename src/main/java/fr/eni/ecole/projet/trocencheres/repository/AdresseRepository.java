package fr.eni.ecole.projet.trocencheres.repository;

import fr.eni.ecole.projet.trocencheres.bo.Adresse;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import java.sql.Statement;

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
            a.setNoAdresse(rs.getInt("no_adresse"));
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

    public int updateAdresse(Adresse a) {
        return jdbc.update("update ADRESSES set rue = ?, code_postal = ?, ville = ? where no_adresse = ?",
                a.getRue(), a.getCodePostal(), a.getVille(), a.getNoAdresse());
    }

    public int insertAdresse(Adresse a) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into ADRESSES (rue, code_postal, ville, adresse_eni) values (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, a.getRue());
            ps.setString(2, a.getCodePostal());
            ps.setString(3, a.getVille());
            ps.setBoolean(4, a.isAdresseEni());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        if (key != null) {
            int id = key.intValue();
            a.setNoAdresse(id);
            return id;
        }
        return 0;
    }
}
