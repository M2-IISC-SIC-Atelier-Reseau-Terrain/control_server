package fr.cyu.rt.control.api.stomp.house.camera;

import fr.cyu.rt.control.services.camera.CameraRegistry;
import fr.cyu.rt.control.services.sensor.SensorRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Random;

/**
 * @author Aldric Vitali Silvestre
 */
@Controller
public class CameraMessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CameraMessageController.class);

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private SensorRegistry sensorRegistry;

    @Autowired
    private CameraRegistry cameraRegistry;

    private Random random = new Random();

    @MessageMapping("/house/camera")
    public void receiveCameraImage(CameraDataMessage message) throws Exception {
        String content = message.content();
        byte[] binaryData = cameraRegistry.updateFromBase64(content);
        LOGGER.debug("Camera image of length {} received", binaryData.length);
        LOGGER.debug("String received is of length {}", content.length());
        template.convertAndSend("/topic/user/camera", binaryData);
    }

//    @Scheduled(fixedDelay = 200, timeUnit = TimeUnit.MILLISECONDS)
    public void sendRandomImage() throws Exception {
//        LOGGER.trace("Send random image");
//        byte b = (byte) random.nextInt(128);
        var binaryData = new byte[640 * 480];
//        for (int i = 0; i < (640 * 480); i++) {
//            binaryData[i] = b;
//        }
        random.nextBytes(binaryData);
        template.convertAndSend("/topic/user/camera", binaryData);
    }

    record CameraDataMessage(
            String content
    ) {}
}
