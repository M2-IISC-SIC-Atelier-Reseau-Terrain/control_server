package fr.cyu.rt.control.api.stomp.event;

import fr.cyu.rt.control.business.event.EventType;
import fr.cyu.rt.control.business.sensor.SensorType;

import java.time.LocalDateTime;

/**
 * The base notification to send to all users
 * subscribed
 *
 * @author Aldric Vitali Silvestre
 */
public record Notification(
        String sensorId,
        EventType eventType,
        SensorType sensorType,
        LocalDateTime timestamp
) {
}
