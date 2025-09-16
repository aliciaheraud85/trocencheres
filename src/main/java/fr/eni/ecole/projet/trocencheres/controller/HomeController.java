package fr.eni.ecole.projet.trocencheres.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import fr.eni.ecole.projet.trocencheres.service.ArticleAVendreService;
import fr.eni.ecole.projet.trocencheres.bo.ArticleAVendre;
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
        return "index";
    }

    @GetMapping("/hello")
    public String helloWorld(){
        return "hello";
    }

}
