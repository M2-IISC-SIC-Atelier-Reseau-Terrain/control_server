package fr.cyu.rt.control.services.auth;

import fr.cyu.rt.control.config.properties.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.security.KeyStore;
import java.util.Date;
import java.util.UUID;

/**
 * @author Aldric Vitali Silvestre
 */
@Service
public class JwtTokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenService.class);

    private final JwtProperties jwtProperties;
    private final Key key;
    private final SignatureAlgorithm signatureAlgorithm;

    @Autowired
    public JwtTokenService(JwtProperties jwtConfiguration, KeyStore keyStore) {
        this.jwtProperties = jwtConfiguration;
        try {
            this.key = keyStore.getKey(
                    jwtConfiguration.keystore().alias(),
                    jwtConfiguration.keystore().keyStorePassword().toCharArray()
            );
            this.signatureAlgorithm = SignatureAlgorithm.valueOf(jwtConfiguration.signatureAlgorithm());
        } catch (Exception e) {
            LOGGER.error("Error while Creating JwtTokenService: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getTokenHeader(HttpServletRequest request) {
        return request.getHeader(jwtProperties.header());
    }

    public String generateToken(Authentication authentication) {
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(
                        "authorities",
                        authentication.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList()
                )
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + jwtProperties.expiration() * 1000))
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch ( ExpiredJwtException
                | UnsupportedJwtException
                | MalformedJwtException
                | SignatureException
                | IllegalArgumentException  ex) {
            LOGGER.error("Token non valid: {}", ex.getMessage());
        }
        return false;
    }
}
