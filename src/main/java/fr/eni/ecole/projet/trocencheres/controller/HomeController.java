package fr.eni.ecole.projet.trocencheres.controller;

import fr.eni.ecole.projet.trocencheres.bo.*;
import fr.eni.ecole.projet.trocencheres.dto.UserProfile;
import fr.eni.ecole.projet.trocencheres.repository.ArticleAVendreRepository;
import fr.eni.ecole.projet.trocencheres.repository.EnchereRepository;
import fr.eni.ecole.projet.trocencheres.service.UserService;
import fr.eni.ecole.projet.trocencheres.service.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import java.security.Principal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import fr.eni.ecole.projet.trocencheres.service.ArticleAVendreService;
import fr.eni.ecole.projet.trocencheres.bo.ArticleAVendre;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class HomeController {

    private final ArticleAVendreService articleAVendreService;
    private final UserService userService;
    private final UtilisateurService utilisateurService;
    private final EnchereRepository enchereRepository;
    private final ArticleAVendreRepository articleAVendreRepository;

    public HomeController(ArticleAVendreService articleAVendreService, UtilisateurService utilisateurService, UserService userService, EnchereRepository enchereRepository, ArticleAVendreRepository articleAVendreRepository) {
        this.articleAVendreService = articleAVendreService;
        this.utilisateurService = utilisateurService;
        this.userService = userService;
        this.enchereRepository = enchereRepository;
        this.articleAVendreRepository = articleAVendreRepository;
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

    @GetMapping("/forbidden")
    public String forbidden(){
        return "forbidden";
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

    @GetMapping("/bid")
    public String bid(int id, int amount, Principal principal){
        String status = "";
        ArticleAVendre article = articleAVendreService.getArticleAVendre(id);
        Utilisateur user = userService.getUserProfile(principal.getName()).getUtilisateur();
        if (user.getCredit() > article.getPrixVente() && article.getStatutEnchere() == 1) {
            article.setPrixVente(amount);
            Enchere bid = new Enchere(user.getPseudo(), id, amount, LocalDateTime.now());
//            TODO: remplacer les appels au repo par le service & déplacer la logique métier ici dans le service
            int bidId = enchereRepository.createEnchere(bid);
//            TODO: ajouter la notion d'enchere dans l'objet ArticleAVendre ; ajouter le moyen de récupérer la dernière enchère (par montant de l'enchère ?) pour pouvoir retrouver l'utilisateur qui a remporté l'enchère
            int rowIsUpdated = articleAVendreRepository.updatePrixVente(article);
            if (bidId != 0 && rowIsUpdated != 0) {
                return "redirect:/auction-details?id=" + id +"&status=" + status;
            }
        }
        return "redirect:/auction-details?error";
    }

    @GetMapping("/add-sale")
    public String addSale(Model  model, Principal principal){
        //Retrieving my logged-in username
        String username = principal != null ? principal.getName() : null;
        UserProfile profile = null;
        if (username != null) {
            profile = userService.getUserProfile(username);
        }
        ArticleAVendre article = new ArticleAVendre();

        //Retrieving my logged in user's address
        if(profile != null && profile.getAdresse() != null){
            article.setNoAdresseRetrait(profile.getAdresse().getNoAdresse());
        }
        model.addAttribute("profile", profile);
        model.addAttribute("article", article);
        model.addAttribute("categories", articleAVendreService.getCategoriesList());
        model.addAttribute("adresse", articleAVendreService.getAdresseList());
        return "add-sale";
    }

    @RequestMapping(value = "/add-sale", method = RequestMethod.POST)
    public String createArticle(@ModelAttribute ArticleAVendre article, Principal principal, HttpServletRequest request){
        if(principal == null){
            return "redirect:/login";
        }

        articleAVendreService.createArticle(article, principal.getName());
        return "redirect:/";
    }


}
