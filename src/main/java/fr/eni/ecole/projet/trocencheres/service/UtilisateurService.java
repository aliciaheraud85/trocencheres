package fr.eni.ecole.projet.trocencheres.service;

import fr.eni.ecole.projet.trocencheres.bo.Adresse;
import fr.eni.ecole.projet.trocencheres.bo.Utilisateur;
import fr.eni.ecole.projet.trocencheres.dto.SignUpRequest;
import fr.eni.ecole.projet.trocencheres.repository.AdresseRepository;
import fr.eni.ecole.projet.trocencheres.repository.UtilisateurRepository;
import fr.eni.ecole.projet.trocencheres.security.SecurityConfig;
import fr.eni.ecole.projet.trocencheres.security.jwt.JWTService;
import fr.eni.ecole.projet.trocencheres.security.jwt.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private AdresseRepository adresseRepository;

    private final PasswordEncoder encoder;

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
}
