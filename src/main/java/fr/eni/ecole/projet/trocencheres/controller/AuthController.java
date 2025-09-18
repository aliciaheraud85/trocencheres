package fr.eni.ecole.projet.trocencheres.controller;

import fr.eni.ecole.projet.trocencheres.security.jwt.JWTService;
import fr.eni.ecole.projet.trocencheres.security.jwt.LoginRequest;
import fr.eni.ecole.projet.trocencheres.security.jwt.LoginResponse;
import fr.eni.ecole.projet.trocencheres.service.UtilisateurService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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

    @Value("${spring.security.jwtExpirationMs}")
    private int jwtExpirationMs;

    @GetMapping("/login")
    public String getLoginForm(LoginRequest loginRequest) {
        return "login";
    }

    @PostMapping("/login")
    public String authenticate(@Valid LoginRequest loginRequest, BindingResult bindingResult, HttpServletResponse response) throws Exception {

        Authentication auth;
        try {
            auth = utilisateurService.validate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (Exception e) {
            return "redirect:/login?error";
        }

        LoginResponse loginResponse = utilisateurService.createLoginResponse(auth, jwtService);

        ResponseCookie cookie = ResponseCookie.from("jwt_auth", loginResponse.getJwtToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(jwtExpirationMs / 1000)
                .sameSite(SameSiteCookies.STRICT.toString())
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        response.setHeader("Location", "/");
        return "redirect:/";
    }

}
