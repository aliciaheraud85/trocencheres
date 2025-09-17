package fr.eni.ecole.projet.trocencheres.service;

import fr.eni.ecole.projet.trocencheres.repository.UtilisateurRepository;
import fr.eni.ecole.projet.trocencheres.security.jwt.JWTService;
import fr.eni.ecole.projet.trocencheres.security.jwt.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private AuthenticationManager authManager;

    public Authentication validate(UsernamePasswordAuthenticationToken token) {
        String actualPassword = utilisateurRepository.getPasswordByPseudo(token.getName());
        return authManager.authenticate(new UsernamePasswordAuthenticationToken(token.getName(), token.getCredentials()));
    }

    public LoginResponse createLoginResponse(Authentication auth, JWTService service) {
        SecurityContextHolder.getContext().setAuthentication(auth);
        UserDetails user = (UserDetails) auth.getPrincipal();
        String jwtToken = service.generateTokenFromUsername(user.getUsername());
        List<String> roles = user.getAuthorities().stream().map(item -> item.getAuthority()).toList();
        return new LoginResponse(user.getUsername(), roles, jwtToken);
    }
}
