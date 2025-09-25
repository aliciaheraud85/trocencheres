package fr.eni.ecole.projet.trocencheres.service;

import fr.eni.ecole.projet.trocencheres.bo.Adresse;
import fr.eni.ecole.projet.trocencheres.bo.ArticleAVendre;
import fr.eni.ecole.projet.trocencheres.bo.Utilisateur;
import fr.eni.ecole.projet.trocencheres.dto.SignUpRequest;
import fr.eni.ecole.projet.trocencheres.repository.AdresseRepository;
import fr.eni.ecole.projet.trocencheres.repository.ArticleAVendreRepository;
import fr.eni.ecole.projet.trocencheres.repository.EnchereRepository;
import fr.eni.ecole.projet.trocencheres.repository.UtilisateurRepository;
import fr.eni.ecole.projet.trocencheres.security.jwt.JWTService;
import fr.eni.ecole.projet.trocencheres.security.jwt.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private AdresseRepository adresseRepository;

    private final PasswordEncoder encoder;
    @Autowired
    private EnchereRepository enchereRepository;
    @Autowired
    private ArticleAVendreRepository articleAVendreRepository;

    public UtilisateurService(PasswordEncoder encoder) {
        this.encoder = new BCryptPasswordEncoder();
    }

    public Authentication validate(UsernamePasswordAuthenticationToken token) {
        String actualPassword = utilisateurRepository.findByPseudo(token.getName()).get().getMotDePasse();
        return authManager.authenticate(new UsernamePasswordAuthenticationToken(token.getName(), token.getCredentials()));
    }

    public LoginResponse createLoginResponse(Authentication auth, JWTService service) {
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = service.generateTokenFromUsername(auth.getPrincipal().toString());
        List<String> roles = auth.getAuthorities().stream().map(item -> item.getAuthority()).toList();
        return new LoginResponse(auth.getPrincipal().toString(), roles, jwtToken);
    }

    public int createUser(SignUpRequest signUpRequest) throws DuplicateKeyException {
        Adresse address = new Adresse(signUpRequest.getStreet(), signUpRequest.getPostalCode(), signUpRequest.getCity(), false);
        int addressId = adresseRepository.insertAdresse(address);
        String hashedPassword = getEncoder().encode(signUpRequest.getPassword());
        Utilisateur user = new Utilisateur(signUpRequest.getUsername(), signUpRequest.getLastName(), signUpRequest.getFirstName(), signUpRequest.getEmail(), signUpRequest.getPhoneNumber(), hashedPassword, 10, false, addressId);
        return utilisateurRepository.addUser(user);
    }

    public PasswordEncoder getEncoder() {
        return encoder;
    }

    public boolean creditOldBidder(int articleId, int amount) throws SQLException {
        Utilisateur oldBidder;
        Optional<Utilisateur> queriedUser = utilisateurRepository.findLastBidder(articleId);
        if (queriedUser.isEmpty()) {
            throw new SQLException("utilisateur not found in database");
        }
        oldBidder = queriedUser.get();
        oldBidder.setCredit(oldBidder.getCredit() + amount);

        int success = utilisateurRepository.updateUtilisateur(oldBidder);
        if (success == 0) {
            throw new SQLException("database utilisateur update failed");
        }
        return true;
    }

    public void creditWinner(int id) throws SQLException {
        try {
            //fixme: doesn't work properly
            Utilisateur bidder = utilisateurRepository.findLastBidder(id).get();
            ArticleAVendre article = articleAVendreRepository.findById(id).get();
            article.setStatutEnchere(3);
            articleAVendreRepository.updateArticleAVendre(article);
            bidder.setCredit(bidder.getCredit() - article.getPrixVente());
            utilisateurRepository.updateUtilisateur(bidder);
            Utilisateur seller = utilisateurRepository.findByPseudo(article.getIdUtilisateur()).get();
            seller.setCredit(seller.getCredit() + article.getPrixVente());
            utilisateurRepository.updateUtilisateur(seller);
        }
        catch (Exception e){
            throw new SQLException("database utilisateur update failed");
        }
    }
}
