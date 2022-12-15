package fr.cyu.rt.control.api.stomp.user.camera;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author Aldric Vitali Silvestre
 */
@Controller
public class UserCameraMessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCameraMessageController.class);

    @MessageMapping("/user/camera")
    @SendTo("/topic/house/camera")
    public CameraCommandMessage sendControlMessage(CameraCommandMessage message) throws Exception {
        LOGGER.trace("User send camera control request");
        return message;
    }
}
