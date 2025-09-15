package fr.eni.ecole.projet.trocencheres.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class EnchereId implements Serializable {
    @Column(name = "id_utilisateur")
    private String idUtilisateur;

    @Column(name = "no_article")
    private Integer noArticle;

    @Column(name = "montant_enchere")
    private Integer montantEnchere;

    public EnchereId() {}

    public EnchereId(String idUtilisateur, Integer noArticle, Integer montantEnchere) {
        this.idUtilisateur = idUtilisateur;
        this.noArticle = noArticle;
        this.montantEnchere = montantEnchere;
    }

    // getters/setters, equals, hashCode
    public String getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(String idUtilisateur) { this.idUtilisateur = idUtilisateur; }
    public Integer getNoArticle() { return noArticle; }
    public void setNoArticle(Integer noArticle) { this.noArticle = noArticle; }
    public Integer getMontantEnchere() { return montantEnchere; }
    public void setMontantEnchere(Integer montantEnchere) { this.montantEnchere = montantEnchere; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnchereId enchereId = (EnchereId) o;
        return Objects.equals(idUtilisateur, enchereId.idUtilisateur) && Objects.equals(noArticle, enchereId.noArticle) && Objects.equals(montantEnchere, enchereId.montantEnchere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUtilisateur, noArticle, montantEnchere);
    }
}

@Entity
@Table(name = "ENCHERES")
public class Enchere {
    @EmbeddedId
    private EnchereId id;

    @Column(name = "date_enchere")
    private LocalDateTime dateEnchere;

    @MapsId("idUtilisateur")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur", insertable = false, updatable = false)
    private Utilisateur utilisateur;

    @MapsId("noArticle")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "no_article", insertable = false, updatable = false)
    private ArticleAVendre article;

    public EnchereId getId() { return id; }
    public void setId(EnchereId id) { this.id = id; }
    public LocalDateTime getDateEnchere() { return dateEnchere; }
    public void setDateEnchere(LocalDateTime dateEnchere) { this.dateEnchere = dateEnchere; }
    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }
    public ArticleAVendre getArticle() { return article; }
    public void setArticle(ArticleAVendre article) { this.article = article; }
}
