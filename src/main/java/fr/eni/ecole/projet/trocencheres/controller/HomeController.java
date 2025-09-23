package fr.eni.ecole.projet.trocencheres.controller;

import fr.eni.ecole.projet.trocencheres.bo.*;
import fr.eni.ecole.projet.trocencheres.dto.UserProfile;
import fr.eni.ecole.projet.trocencheres.service.*;
import jakarta.servlet.http.HttpServletRequest;
import fr.eni.ecole.projet.trocencheres.bo.Adresse;
import fr.eni.ecole.projet.trocencheres.bo.Categorie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import fr.eni.ecole.projet.trocencheres.service.ArticleAVendreService;
import fr.eni.ecole.projet.trocencheres.bo.ArticleAVendre;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

@Controller
public class HomeController {

    private final ArticleAVendreService articleAVendreService;
    private final UserService userService;
    private final UtilisateurService utilisateurService;

    public HomeController(ArticleAVendreService articleAVendreService, UtilisateurService utilisateurService, UserService userService) {
        this.articleAVendreService = articleAVendreService;
        this.userService = userService;
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/")
    public String index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                        @RequestParam(value = "q", required = false) String q,
                        @RequestParam(value = "view", required = false) String view,
                        @RequestParam(value = "status", required = false) Integer status,
                        Principal principal,
                        Model model) {

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
        model.addAttribute("profile", username);
        List<Categorie> lstCategories = articleAVendreService.getCategoriesList();
        model.addAttribute("categories", lstCategories);
        model.addAttribute("selectedCategoryId", categoryId);
        return "index";
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "hello";
    }

    @GetMapping("/forbidden")
    public String forbidden() {
        return "forbidden";
    }

    @GetMapping("/auction-details")
    public String auctionDetails(int id, @RequestParam(name = "status", required = false) String status, Principal principal, Model model) {
        int userCredit;
        if (principal != null) {
            userCredit = userService.getUserProfile(principal.getName()).getUtilisateur().getCredit();
            model.addAttribute("userCredit", userCredit);
        }
        if (id > 0) {
            ArticleAVendre articleById = articleAVendreService.getArticleAVendre(id);
            if (articleById != null) {
                Categorie categorie = articleAVendreService.getCategorieForArticle(id);
                Adresse adresse = articleAVendreService.getAdresseForArticle(id);
                model.addAttribute("article", articleById);
                model.addAttribute("categorie", categorie);
                model.addAttribute("adresse", adresse);
                model.addAttribute("status", status);
                // provide current user and canCancel flag for template
                String currentUser = principal != null ? principal.getName() : null;
                boolean canCancel = currentUser != null && articleAVendreService.isCancelable(id, currentUser);
                model.addAttribute("currentUser", currentUser);
                model.addAttribute("canCancel", canCancel);
                if (articleById.isOnSale() && articleById.getPrixVente() > 0) {
                    try {
                        String highestBidder = articleAVendreService.getHighestBidderUsername(id);
                        model.addAttribute("highestBidder", highestBidder);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return "sale/auction-details";
            } else {
                System.out.println("This article does not exist");
                return "redirect:/index";
            }
        } else {
            System.out.println("This id does not exist");
            return "redirect:/index";
        }
    }

    @PostMapping("/bid")
    public String bid(int id, @RequestParam(name = "inputPrice", required = true) int amount, Principal principal) {
        if (principal == null) {
            return "redirect:/";
        }
        ArticleAVendre article = articleAVendreService.getArticleAVendre(id);
        Utilisateur bidder = userService.getUserProfile(principal.getName()).getUtilisateur();
        int oldCredit = bidder.getCredit();

        if (article.isOnSale() && article.isValidBid(amount)) {
            try {
                boolean bidderDebited = userService.debitBidder(bidder, amount);
                if (article.getPrixVente() != 0 && bidderDebited) {
                    boolean oldBidderCredited = utilisateurService.creditOldBidder(article.getNoArticle(), article.getPrixVente());
                    if (!oldBidderCredited) {
                        userService.updateUserCredit(bidder, oldCredit);
                        return "redirect:/auction-details?error";
                    }
                }
                articleAVendreService.createEnchere(bidder, id, amount);
                articleAVendreService.updateArticlePrice(article, amount);
                return String.format("redirect:/auction-details?id=%d&status=bid_placed", id);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return "redirect:/auction-details?error";
            }
        }
        return String.format("redirect:/auction-details?id=%d", id);
    }

    @GetMapping("/sale/add-sale")
    public String addSale(Model model, Principal principal) {
        //Retrieving my logged-in username
        String username = principal != null ? principal.getName() : null;
        UserProfile profile = null;
        if (username != null) {
            profile = userService.getUserProfile(username);
        }
        ArticleAVendre article = new ArticleAVendre();

        //Retrieving my logged in user's address
        if (profile != null && profile.getAdresse() != null) {
            article.setNoAdresseRetrait(profile.getAdresse().getNoAdresse());
        }
        model.addAttribute("profile", profile);
        model.addAttribute("article", article);
        model.addAttribute("categories", articleAVendreService.getCategoriesList());
        model.addAttribute("adresse", articleAVendreService.getAdresseList());
        return "sale/add-sale";
    }

    @RequestMapping(value = "/sale/add-sale", method = RequestMethod.POST)
    public String createArticle(@ModelAttribute ArticleAVendre article, Principal principal, HttpServletRequest request) {
        if (principal == null) {
            return "redirect:user/login";
        }
        articleAVendreService.createArticle(article, principal.getName());
        return "redirect:/";
    }

    @PostMapping("/auction/cancel")
    public String cancelAuction(int id, Principal principal, Model model) {
        String username = principal != null ? principal.getName() : null;
        if (username == null) return "redirect:/login";
        boolean ok = articleAVendreService.cancelAuction(id, username);
        // redirect back to details with a query param indicating result
        return "redirect:/auction-details?id=" + id + (ok ? "&cancelled=1" : "&cancelled=0");
    }


    @PostMapping("/sale/modif-sale")
    public String updateArticleAVendre(@ModelAttribute ArticleAVendre article, Principal principal, @RequestParam("id") int id) {
        if (principal == null) {
            return "redirect:/login";
        }
        try {
            articleAVendreService.updateArticleAVendre(article);
            article.setNoArticle(id);
            articleAVendreService.updateArticleAVendre(article);

            return "redirect:/";
        } catch (RuntimeException e) {
            return "redirect:/error" + e.getMessage();
        }
    }

    @GetMapping("/sale/modif-sale")
    public String editArticle(@RequestParam("id") int id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        ArticleAVendre article = articleAVendreService.getArticleAVendre(id);
        if (article == null) {
            return "redirect:/index";
        }
        model.addAttribute("article", article);
        model.addAttribute("categories", articleAVendreService.getCategoriesList());
        model.addAttribute("adresse", articleAVendreService.getAdresseList());
        return "sale/modif-sale";
    }


}
