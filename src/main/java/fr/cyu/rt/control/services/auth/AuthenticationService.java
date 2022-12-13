package fr.cyu.rt.control.services.auth;

import fr.cyu.rt.control.api.rest.auth.ReqLogin;
import fr.cyu.rt.control.business.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Aldric Vitali Silvestre
 */
@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserDetailsService userDetailsService;

    public String createToken(ReqLogin request) {
        String name = request.name();
        String password = request.password();

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(name, password));

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        return jwtTokenService.generateToken(authentication);
    }

    public String refreshToken(String token) {
        // The user doing this request IS connected
        // If it is not the case, then he must log in properly
        Authentication authentication = Optional.of(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .orElseThrow(() -> new NoSuchElementException("No authentication found, please log in"));

        // We still need to check if we can trust this user
        String userName = jwtTokenService.getUsernameFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        return jwtTokenService.generateToken(authentication);
    }

    public User retrieveUser() {
        return Optional.of(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .flatMap(this::castToUser)
                .orElseThrow(() -> new NoSuchElementException("No authentication nor principal found"));
    }

    private Optional<User> castToUser(Object principal) {
        if (principal instanceof User user) {
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
