package fr.eni.ecole.projet.trocencheres.controller;

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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;

@Controller
public class AuthController {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Value("${spring.security.jwtExpirationMs}")
    private int jwtExpirationMs;

    @GetMapping("/register")
    public String getRegisterForm(@ModelAttribute("signUpRequest") SignUpRequest signUpRequest) {
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("signUpRequest") SignUpRequest signUpRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/register";
        }
        try {
            int id = utilisateurService.createUser(signUpRequest);
        } catch (DuplicateKeyException e) {
            return "redirect:user/register?error";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:user/register?error";
        }
        return "redirect:user/login";
    }

    @GetMapping("/login")
    public String getLoginForm(@ModelAttribute("loginRequest") LoginRequest loginRequest) {
        if (isAuthenticated()) {
            return "redirect:/";
        }
        return "user/login";
    }

    @PostMapping("/login")
    public String authenticate(@Valid @ModelAttribute("loginRequest") LoginRequest loginRequest, HttpServletResponse response) {

        Authentication auth;
        try {
            auth = utilisateurService.validate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (Exception e) {
            return "redirect:user/login?error";
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

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

}
