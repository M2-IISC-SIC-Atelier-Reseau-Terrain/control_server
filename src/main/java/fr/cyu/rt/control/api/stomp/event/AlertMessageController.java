package fr.cyu.rt.control.api.stomp.event;

import fr.cyu.rt.control.business.event.EventType;
import fr.cyu.rt.control.business.sensor.SensorType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

/**
 * @author Aldric Vitali Silvestre
 */
@Controller
public class AlertMessageController {

    @MessageMapping("/alert")
    @SendTo("/topic/notifications")
    public Notification onAlertReceived(Alert alert) throws Exception {
        // TODO store the event
        return new Notification(
                alert.sensorId(),
                EventType.ALERT,
                alert.sensorType(),
                alert.timestamp()
        );
    }

    /**
     * Has automatically the EventType ALERT
     */
    record Alert(
        String sensorId,
        SensorType sensorType,
        LocalDateTime timestamp
    ) {
    }
}
