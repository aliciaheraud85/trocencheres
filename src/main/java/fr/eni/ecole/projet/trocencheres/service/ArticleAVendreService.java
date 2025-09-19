package fr.eni.ecole.projet.trocencheres.service;

import fr.eni.ecole.projet.trocencheres.bo.Adresse;
import fr.eni.ecole.projet.trocencheres.bo.Categorie;
import fr.eni.ecole.projet.trocencheres.repository.AdresseRepository;
import fr.eni.ecole.projet.trocencheres.repository.CategorieRepository;
import org.springframework.stereotype.Service;
import fr.eni.ecole.projet.trocencheres.bo.ArticleAVendre;

import fr.eni.ecole.projet.trocencheres.repository.ArticleAVendreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleAVendreService {

    private final ArticleAVendreRepository articleRepository;
    private final CategorieRepository categorieRepository;
    private final AdresseRepository adresseRepository;

    public ArticleAVendreService(ArticleAVendreRepository articleRepository,  CategorieRepository categorieRepository, AdresseRepository adresseRepository) {
        this.articleRepository = articleRepository;
        this.categorieRepository = categorieRepository;
        this.adresseRepository = adresseRepository;
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

    public List<Categorie> getCategoriesList(){
        return categorieRepository.findAll();
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

}
