package fr.eni.ecole.projet.trocencheres.service;

import fr.eni.ecole.projet.trocencheres.bo.*;
import fr.eni.ecole.projet.trocencheres.repository.*;
import org.springframework.expression.spel.ast.OpPlus;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleAVendreService {

    private final ArticleAVendreRepository articleRepository;
    private final CategorieRepository categorieRepository;
    private final AdresseRepository adresseRepository;
    private final EnchereRepository enchereRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurService utilisateurService;
    private final UserService userService;

    public ArticleAVendreService(ArticleAVendreRepository articleRepository, CategorieRepository categorieRepository, AdresseRepository adresseRepository, EnchereRepository enchereRepository, UtilisateurRepository utilisateurRepository, UtilisateurService utilisateurService, UserService userService) {
        this.articleRepository = articleRepository;
        this.categorieRepository = categorieRepository;
        this.adresseRepository = adresseRepository;
        this.enchereRepository = enchereRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurService = utilisateurService;
        this.userService = userService;
    }

    public List<ArticleAVendre> getAuctionList(){
        return articleRepository.findAll();
    }

    public List<ArticleAVendre> getAuctionList(Integer categoryId){
        if (categoryId == null) return getAuctionList();
        return articleRepository.findByCategory(categoryId);
    }

    public List<ArticleAVendre> getAuctionList(Integer categoryId, String nameQuery){
        String pattern = nameQuery == null || nameQuery.isBlank() ? null : ("%" + nameQuery + "%");
        if (categoryId == null) {
            if (pattern == null) return getAuctionList();
            return articleRepository.findByName(pattern);
        } else {
            if (pattern == null) return articleRepository.findByCategory(categoryId);
            return articleRepository.findByCategoryAndName(categoryId, pattern);
        }
    }

    public List<ArticleAVendre> getAuctionsForParticipant(String username){
        return articleRepository.findByParticipant(username);
    }

    public List<ArticleAVendre> getAuctionsWonByUser(String username){
        return articleRepository.findWonByUser(username);
    }

    public List<ArticleAVendre> getAuctionsForSeller(String sellerId, Integer statutEnchere){
        return articleRepository.findBySellerAndStatus(sellerId, statutEnchere);
    }

    public boolean cancelAuction(int articleId, String sellerId) {
        ArticleAVendre article = getArticleAVendre(articleId);
        if (article == null) return false;
        // Only seller can cancel
        if (!sellerId.equals(article.getIdUtilisateur())) return false;
        // Do not cancel if already canceled
        if (article.getStatutEnchere() == 100) return false;
        // Can cancel only if dateDebutEncheres is in the future (not started)
        if (article.getDateDebutEncheres() != null && article.getDateDebutEncheres().isAfter(java.time.LocalDate.now())) {
            int updated = articleRepository.cancelArticle(articleId);
            return updated > 0;
        }
        return false;
    }

    public boolean isCancelable(int articleId, String sellerId) {
        ArticleAVendre article = getArticleAVendre(articleId);
        if (article == null) return false;
        if (!sellerId.equals(article.getIdUtilisateur())) return false;
        // Not cancelable if already canceled
        if (article.getStatutEnchere() == 100) return false;
        return article.getDateDebutEncheres() != null && article.getDateDebutEncheres().isAfter(java.time.LocalDate.now());
    }

    public List<Categorie> getCategoriesList(){
        return categorieRepository.findAll();
    }

    public List<Adresse> getAdresseList(){
        return adresseRepository.findAll();
    }

    public ArticleAVendre getArticleAVendre(int id){
        return articleRepository.findById(id).orElse(null);
    }


    public Categorie getCategorieForArticle(int articleId){
        ArticleAVendre article = articleRepository.findById(articleId).orElse(null);
        if(article!=null){
            return categorieRepository.findById(article.getNoCategorie()).orElse(null);
        }else{
            return null;
        }
    }

    public Adresse getAdresseForArticle(int articleId){
        ArticleAVendre article = articleRepository.findById(articleId).orElse(null);
        if(article!=null){
            return adresseRepository.findById(article.getNoAdresseRetrait()).orElse(null);
        }else{
            return null;
        }
    }

    public void updateArticle(ArticleAVendre article) throws SQLException {
        int success = articleRepository.updatePrixVente(article);
        if (success == 0) {
            throw new SQLException("database article update failed");
        }
    }

    public void updateArticlePrice(ArticleAVendre article, int amount) throws SQLException {
        article.setPrixVente(amount);
        updateArticle(article);
    }

    public int createArticle(ArticleAVendre article, String username){
        article.setIdUtilisateur(username);
        article.setStatutEnchere(0);
        return articleRepository.createArticleAVendre(article);
    }


    public void createEnchere(Utilisateur bidder, int id, int amount) throws SQLException {
        Enchere bid = new Enchere(bidder.getPseudo(), id, amount, LocalDateTime.now());
        int success = enchereRepository.createEnchere(bid);
        if (success == 0) {
            throw new SQLException("database enchere insert failed");
        }
    }

    public Utilisateur getHighestBidder(int articleId) {
        Optional<Utilisateur> user = utilisateurRepository.findLastBidder(articleId);
        return user.orElse(null);
    }

    public String getHighestBidderUsername(int articleId) throws SQLException {
        Optional<String> result = enchereRepository.findLastBidderUsername(articleId);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new SQLException("Bidder not found");
        }
    }

    public void updateArticleAVendre(ArticleAVendre updatedArticle){
        if (updatedArticle == null) {
            throw new IllegalArgumentException("L'article à mettre à jour ne peut pas être null");
        }

        int rowsUpdated = articleRepository.updateArticleAVendre(updatedArticle);
        if (rowsUpdated == 0) {
            throw new RuntimeException("Aucun article trouvé avec l'id " + updatedArticle.getNoArticle());

        }
    }
}
