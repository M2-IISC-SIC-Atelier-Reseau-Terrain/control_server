package fr.cyu.rt.control.api.stomp.house.alert;

import fr.cyu.rt.control.api.stomp.event.Notification;
import fr.cyu.rt.control.business.event.EventType;
import fr.cyu.rt.control.business.sensor.SensorType;
import fr.cyu.rt.control.services.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Aldric Vitali Silvestre
 */
@Controller
public class HouseAlertMessageController {

    @Autowired
    private EventService eventService;

    @MessageMapping("/house/alert")
    @SendTo("/topic/notifications")
    public Notification onAlertReceived(Alert alert) throws Exception {
        eventService.onAlertReceived(alert);
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
    public record Alert(
        String sensorId,
        SensorType sensorType,
        LocalDateTime timestamp,
        Optional<String> value
    ) {
    }
}
