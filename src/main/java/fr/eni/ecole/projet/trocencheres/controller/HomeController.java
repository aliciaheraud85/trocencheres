package fr.eni.ecole.projet.trocencheres.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import fr.eni.ecole.projet.trocencheres.bo.Adresse;
import fr.eni.ecole.projet.trocencheres.bo.Categorie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import fr.eni.ecole.projet.trocencheres.service.ArticleAVendreService;
import fr.eni.ecole.projet.trocencheres.bo.ArticleAVendre;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class HomeController {

    private final ArticleAVendreService articleAVendreService;

    public HomeController(ArticleAVendreService articleAVendreService) {
        this.articleAVendreService = articleAVendreService;
    }

    @GetMapping("/")
    public String index(@RequestParam(value = "categoryId", required = false) Integer categoryId, Model model){
        List<ArticleAVendre> lstArticles = articleAVendreService.getAuctionList(categoryId);
        model.addAttribute("articles", lstArticles);

        List<Categorie> lstCategories = articleAVendreService.getCategoriesList();
        model.addAttribute("categories", lstCategories);
        model.addAttribute("selectedCategoryId", categoryId);
        return "index";
    }

    @GetMapping("/hello")
    public String helloWorld(){
        return "hello";
    }

    @GetMapping("/auction-details")
    public String auctionDetails(int id, Model model){
        if(id > 0){
            ArticleAVendre articleById = articleAVendreService.getArticleAVendre(id);
            if(articleById != null) {
                Categorie categorie = articleAVendreService.getCategorieForArticle(id);
                Adresse adresse = articleAVendreService.getAdresseForArticle(id);
                model.addAttribute("article", articleById);
                model.addAttribute("categorie", categorie);
                model.addAttribute("adresse", adresse);
                return "auction-details";
            }else {
                System.out.println("This article does not exist");
                return "redirect:/index";
            }
        }else{
            System.out.println("This id does not exist");
            return "redirect:/index";
        }
    }


}
