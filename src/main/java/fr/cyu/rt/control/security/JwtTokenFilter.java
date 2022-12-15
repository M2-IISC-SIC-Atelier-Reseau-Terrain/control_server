package fr.cyu.rt.control.security;

import fr.cyu.rt.control.business.user.User;
import fr.cyu.rt.control.config.properties.JwtProperties;
import fr.cyu.rt.control.dao.user.UserDao;
import fr.cyu.rt.control.services.auth.JwtTokenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author Aldric Vitali Silvestre
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenFilter.class);

    private final JwtProperties jwtConfiguration;
    private final JwtTokenService tokenService;
    private final UserDao userDao;

    @Autowired
    public JwtTokenFilter(JwtProperties jwtConfiguration,
                          JwtTokenService tokenService,
                          UserDao userDao) {
        this.jwtConfiguration = jwtConfiguration;
        this.tokenService = tokenService;
        this.userDao = userDao;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        findTokenFromHeaders(request)
                .ifPresent(token -> loadToken(request, token));

        filterChain.doFilter(request, response);
    }

    private Optional<String> findTokenFromHeaders(HttpServletRequest request) {
        String authToken = request.getHeader(jwtConfiguration.header());
        if (StringUtils.isBlank(authToken)) {
            return Optional.empty();
        }
        LOGGER.trace("Found token from headers");
        return Optional.of(authToken);
    }

    private void loadToken(HttpServletRequest request, String token) {
        // We want to provide the user directly to the context in order to be easily retrieved without requesting
        // again for the user
        if (!tokenService.validateToken(token)) {
            SecurityContextHolder.clearContext();
            return;
        }

        String username = tokenService.getUsernameFromToken(token);
        User user = userDao.findByNameOrThrow(username);
        JwtUserDetails userDetails = new JwtUserDetails(user);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user,
                null,
                userDetails.getAuthorities()
        );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
