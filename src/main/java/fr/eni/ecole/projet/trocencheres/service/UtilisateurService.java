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
        String actualPassword = utilisateurRepository.findByPseudo(token.getName()).get().getMotDePasse();
        return authManager.authenticate(new UsernamePasswordAuthenticationToken(token.getName(), token.getCredentials()));
    }

    public LoginResponse createLoginResponse(Authentication auth, JWTService service) {
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = service.generateTokenFromUsername(auth.getPrincipal().toString());
        List<String> roles = auth.getAuthorities().stream().map(item -> item.getAuthority()).toList();
        return new LoginResponse(auth.getPrincipal().toString(), roles, jwtToken);
    }
}
