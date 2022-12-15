package fr.cyu.rt.control.security;

import fr.cyu.rt.control.config.properties.JwtProperties;
import fr.cyu.rt.control.services.auth.AuthenticationService;
import fr.cyu.rt.control.services.auth.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

/**
 * @author Aldric Vitali Silvestre
 */
public class WebSocketChannelInterceptor implements ChannelInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketChannelInterceptor.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        Optional.ofNullable(accessor)
                .filter(a -> StompCommand.CONNECT.equals(a.getCommand()))
                .ifPresent(a -> {
                    // If the user is already here, we are already authenticated
                    Object simpUser = a.getHeader("simpUser");
                    if (simpUser instanceof UsernamePasswordAuthenticationToken user) {
                        // Already connected
                        LOGGER.debug("WS: user is already connceted");
                        return;
                    }

                    LOGGER.debug("WS: find token for user");
                    String token = a.getFirstNativeHeader(jwtProperties.header());

                    String userName = jwtTokenService.getUsernameFromToken(token);
                    JwtUserDetails userDetails = (JwtUserDetails) userDetailsService.loadUserByUsername(userName);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    a.setUser(authentication);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                });
        return message;
    }
}
