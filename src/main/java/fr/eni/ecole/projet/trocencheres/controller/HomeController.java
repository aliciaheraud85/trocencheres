package fr.eni.ecole.projet.trocencheres.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import fr.eni.ecole.projet.trocencheres.bo.Categorie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import fr.eni.ecole.projet.trocencheres.service.ArticleAVendreService;
import fr.eni.ecole.projet.trocencheres.bo.ArticleAVendre;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class HomeController {

    private final ArticleAVendreService articleAVendreService;

    public HomeController(ArticleAVendreService articleAVendreService) {
        this.articleAVendreService = articleAVendreService;
    }

    @GetMapping("/")
    public String index(Model model){
        LocalDate localDate = LocalDate.now();
        String formattedDate = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        model.addAttribute("currentDateFormatted", formattedDate);

        List<ArticleAVendre> lstArticles = articleAVendreService.getAuctionList();
        model.addAttribute("articles", lstArticles);

        List<Categorie> lstCategories = articleAVendreService.getCategoriesList();
        model.addAttribute("categories", lstCategories);
        return "index";
    }

    @GetMapping("/hello")
    public String helloWorld(){
        return "hello";
    }

    @GetMapping("/auction-details")
    public void auctionDetails(@RequestParam int id){
        if(id > 0){
            ArticleAVendre articleById = articleAVendreService.getArticleAVendre(id);
            if(articleById != null)
                System.out.println(articleById);
            else
                System.out.println("This article does not exist");

        }else{
            System.out.println("This id does not exist");
        }
    }


}
