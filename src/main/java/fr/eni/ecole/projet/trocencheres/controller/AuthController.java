package fr.eni.ecole.projet.trocencheres.controller;

import ch.qos.logback.core.model.Model;
import fr.eni.ecole.projet.trocencheres.dto.SignUpRequest;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;

import javax.naming.Binding;

@Controller
public class AuthController {

    @Autowired
    JWTService jwtService;

    @Autowired
    UtilisateurService utilisateurService;

    @Value("${spring.security.jwtExpirationMs}")
    private int jwtExpirationMs;

    @GetMapping("/register")
    public String getRegisterForm(SignUpRequest signUpRequest) {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute SignUpRequest signUpRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        utilisateurService.createUser(signUpRequest);
        return "login";
    }

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
