package fr.cyu.rt.control.api.stomp.house.camera;

import fr.cyu.rt.control.services.event.EventService;
import fr.cyu.rt.control.services.sensor.SensorRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.BinaryMessage;

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

    @Autowired
    private SensorRegistry sensorRegistry;

    private Random random = new Random();

    @MessageMapping("/house/camera")
    public void receiveCameraImage(BinaryMessage message) throws Exception {
        LOGGER.debug("Camera image received");
        byte[] array = message.getPayload().array();
        template.convertAndSend("/topic/user/camera", array);
    }

    @Scheduled(fixedDelay = 200, timeUnit = TimeUnit.MILLISECONDS)
    public void sendRandomImage() throws Exception {
//        LOGGER.trace("Send random image");
        byte b = (byte) random.nextInt(255);
        var binaryData = new byte[640 * 480];
        for (int i = 0; i < (640 * 480); i++) {
            binaryData[i] = b;
        }
//        random.nextBytes(binaryData);
        template.convertAndSend("/topic/user/camera", binaryData);
    }
}
