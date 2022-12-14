package fr.cyu.rt.control.api.stomp.house.camera;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * @author Aldric Vitali Silvestre
 */
public class CameraMessageController {

    @Autowired
    private SimpMessagingTemplate template;

    // TODO create message path for receiving and sending images
    @MessageMapping("/app/house/camera")
    public void receiveCameraImage(Message<?> message) throws Exception {
        // TODO receive binary data ?
        // TODO send to /topic/camera
    }
}
