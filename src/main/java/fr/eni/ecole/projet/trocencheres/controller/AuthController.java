package fr.eni.ecole.projet.trocencheres.controller;

import fr.eni.ecole.projet.trocencheres.security.jwt.JWTService;
import fr.eni.ecole.projet.trocencheres.security.jwt.LoginRequest;
import fr.eni.ecole.projet.trocencheres.security.jwt.LoginResponse;
import fr.eni.ecole.projet.trocencheres.service.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;

@Controller
public class AuthController {

    @Autowired
    JWTService jwtService;

    @Autowired
    UtilisateurService utilisateurService;

    @GetMapping("/login")
    public String getLoginForm(LoginRequest loginRequest) {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid LoginRequest loginRequest, BindingResult bindingResult) throws Exception {

        Authentication auth;
        try {
            auth = utilisateurService.validate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        LoginResponse response = utilisateurService.createLoginResponse(auth, jwtService);
        System.out.println("token: " + response.getJwtToken());
        return ResponseEntity.ok(response);
    }

}
