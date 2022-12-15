package fr.cyu.rt.control.api.stomp.house.alert;

import fr.cyu.rt.control.api.stomp.user.event.Notification;
import fr.cyu.rt.control.business.event.EventType;
import fr.cyu.rt.control.business.sensor.SensorType;
import fr.cyu.rt.control.services.event.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseAlertMessageController.class);

    @Autowired
    private EventService eventService;

    @MessageMapping("/house/alert")
    @SendTo("/topic/notifications")
    public Notification onAlertReceived(Alert alert) throws Exception {
        LOGGER.debug("Alert received");
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
            AlertType alertType,
            String sensorId,
            SensorType sensorType,
            LocalDateTime timestamp,
            Optional<String> value
    ) {
    }

    public enum AlertType {
        ALERT(EventType.ALERT),
        ALERT_END(EventType.ALERT_END),
        ;

        private EventType eventType;

        AlertType(EventType eventType) {
            this.eventType = eventType;
        }

        public EventType getEventType() {
            return eventType;
        }
    }
}
