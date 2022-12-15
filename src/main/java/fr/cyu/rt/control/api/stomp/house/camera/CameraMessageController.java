package fr.cyu.rt.control.api.stomp.house.camera;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Aldric Vitali Silvestre
 */
@Controller
public class CameraMessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CameraMessageController.class);

    @Autowired
    private SimpMessagingTemplate template;

    private Random random = new Random();

    // TODO create message path for receiving and sending images
    @MessageMapping("/house/camera")
    public void receiveCameraImage(Message<?> message) throws Exception {
        // TODO receive binary data ?
        // TODO send to /topic/camera
        // TODO Always save if in alert ?
    }

    @Scheduled(fixedDelay = 200, timeUnit = TimeUnit.MILLISECONDS)
    public void sendRandomImage() throws Exception {
//        LOGGER.trace("Send random image");
        var binaryData = new byte[640 * 480];
        random.nextBytes(binaryData);
        template.convertAndSend("/topic/user/camera", binaryData);
    }
}
