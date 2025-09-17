package fr.eni.ecole.projet.trocencheres.service;

import fr.eni.ecole.projet.trocencheres.bo.Categorie;
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

    public ArticleAVendreService(ArticleAVendreRepository articleRepository,  CategorieRepository categorieRepository) {
        this.articleRepository = articleRepository;
        this.categorieRepository = categorieRepository;
    }

    public List<ArticleAVendre> getAuctionList(){
        return articleRepository.findAll();
    }

    public List<Categorie> getCategoriesList(){
        return categorieRepository.findAll();
    }

    public ArticleAVendre getArticleAVendre(int id){
        return articleRepository.findById(id).orElse(null);
    }

}
