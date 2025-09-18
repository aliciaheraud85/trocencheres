package fr.eni.ecole.projet.trocencheres.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

@Service
public class JWTService {

// FIXME: add logging (slf4j or eq)

    private final String secretKey;

    @Value("${spring.security.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${spring.application.state}")
    private String appState;

    @Value("${spring.security.overrideSecretKey}")
    private String overrideSecretKey;

    public JWTService() {
        if (this.appState != "dev" || this.appState == null) {
            try {
                SecretKey key = KeyGenerator.getInstance("HmacSHA256").generateKey();
                secretKey = Base64.getEncoder().encodeToString(key.getEncoded());
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        } else {
            this.secretKey = overrideSecretKey;
        }
    }

    public String getJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && Arrays.stream(cookies).anyMatch(cookie -> cookie.getName().equals("jwt_auth"))) {
            String jwt = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("jwt_auth")).findFirst().get().getValue();
            return jwt;
        }
        return null;
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Token is invalid: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("Token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }
}
