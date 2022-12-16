package fr.cyu.rt.control.api.stomp;

import fr.cyu.rt.control.business.user.User;
import fr.cyu.rt.control.business.user.UserRole;
import fr.cyu.rt.control.security.JwtUserDetails;
import fr.cyu.rt.control.services.camera.CameraRegistry;
import fr.cyu.rt.control.services.event.EventService;
import fr.cyu.rt.control.services.user.ConnectedUserRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * This is a listener that will know when any user will connect / disconnect
 *
 * @author Aldric Vitali Silvestre
 */
@Component
public class WebsocketEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketEventListener.class);

    @Autowired
    private ConnectedUserRegistry userRegistry;

    @Autowired
    private CameraRegistry cameraRegistry;

    @Autowired
    private EventService eventService;

    @EventListener
    public void onConnection(SessionConnectedEvent event) {
        JwtUserDetails userDetails = retrieveUserDetails(event);

        // TODO if the user is a COM one, activate catching here ?

        Long userId = userDetails.getId();
        LOGGER.info("User \"{}\" connected", userDetails.getUsername());
        if (userRegistry.isUserConnected(userId)) {
            LOGGER.warn("The same user ({}) is already connected on websocket, are there two connections ?",
                        userDetails.getUsername()
            );
        } else {
            userRegistry.onUserConnected(userId);
        }
    }

    @EventListener
    public void onDisconnection(SessionDisconnectEvent event) {
        JwtUserDetails userDetails = retrieveUserDetails(event);
        Long id = userDetails.getId();
        LOGGER.info("User \"{}\" disconnected", userDetails.getUsername());
        if (!userRegistry.isUserConnected(id)) {
            LOGGER.warn("The same user ({}) is already disconnected on websocket, are there two connections ?",
                        userDetails.getUsername()
            );
        }
        // If this is a user, we have to handle the end control
        User user = userDetails.getUser();
        if (user.getRole() == UserRole.USER) {
            if (cameraRegistry.isUserControlling()) {
                LOGGER.debug("Deactivate the camera control");
                eventService.onCameraControlEnd();
            }
        }
        userRegistry.onUserDisconnected(id);
    }

    private JwtUserDetails retrieveUserDetails(AbstractSubProtocolEvent event) {
        UsernamePasswordAuthenticationToken userAuthentication = (UsernamePasswordAuthenticationToken) event.getUser();
        JwtUserDetails userDetails = (JwtUserDetails) userAuthentication.getPrincipal();
        return userDetails;
    }

}
