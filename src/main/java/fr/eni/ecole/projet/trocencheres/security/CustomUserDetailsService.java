//package fr.eni.ecole.projet.trocencheres.security;

//import fr.eni.ecole.projet.trocencheres.bo.Utilisateur;
//import fr.eni.ecole.projet.trocencheres.repository.UtilisateurRepository;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;

//import java.util.ArrayList;
//import java.util.List;

//@Service
//public class CustomUserDetailsService implements UserDetailsService {

//    private final UtilisateurRepository utilisateurRepository;

//    public CustomUserDetailsService(UtilisateurRepository utilisateurRepository) {
//        this.utilisateurRepository = utilisateurRepository;
//    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Utilisateur u = utilisateurRepository.findByPseudo(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + username));

//       List<GrantedAuthority> authorities = new ArrayList<>();
//       authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//       if (u.isAdministrateur()) {
//           authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//       }

//      return User.withUsername(u.getPseudo())
//              .password(u.getMotDePasse())
//              .authorities(authorities)
//              .build();
//  }
//}
