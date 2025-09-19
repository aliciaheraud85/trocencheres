package fr.eni.ecole.projet.trocencheres.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import fr.eni.ecole.projet.trocencheres.bo.Adresse;
import fr.eni.ecole.projet.trocencheres.bo.Categorie;
import org.springframework.stereotype.Controller;
import java.security.Principal;
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
    public String index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                        @RequestParam(value = "q", required = false) String q,
                        @RequestParam(value = "view", required = false) String view,
                        @RequestParam(value = "status", required = false) Integer status,
                        Principal principal,
                        Model model){

        List<ArticleAVendre> lstArticles;
        String username = principal != null ? principal.getName() : null;

        if (username != null && view != null) {
            switch (view) {
                case "participating":
                    lstArticles = articleAVendreService.getAuctionsForParticipant(username);
                    break;
                case "won":
                    lstArticles = articleAVendreService.getAuctionsWonByUser(username);
                    break;
                case "mySales":
                    // status may be null (all), or 0/1/2
                    lstArticles = articleAVendreService.getAuctionsForSeller(username, status);
                    break;
                default:
                    // fallback to general search with category + name
                    lstArticles = articleAVendreService.getAuctionList(categoryId, q);
            }
        } else {
            // not authenticated or no special view param: regular public list
            lstArticles = articleAVendreService.getAuctionList(categoryId, q);
        }
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
