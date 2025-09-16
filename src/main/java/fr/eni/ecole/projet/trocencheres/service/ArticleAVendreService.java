package fr.eni.ecole.projet.trocencheres.service;

import org.springframework.stereotype.Service;
import fr.eni.ecole.projet.trocencheres.bo.ArticleAVendre;

import fr.eni.ecole.projet.trocencheres.repository.ArticleAVendreRepository;

import java.util.List;

@Service
public class ArticleAVendreService {

    private final ArticleAVendreRepository articleRepository;

    public ArticleAVendreService(ArticleAVendreRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<ArticleAVendre> getAuctionList(){
        return articleRepository.findAll();
    }
}
