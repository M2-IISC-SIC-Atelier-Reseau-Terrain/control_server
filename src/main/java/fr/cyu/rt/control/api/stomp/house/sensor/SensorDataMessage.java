package fr.cyu.rt.control.api.stomp.house.sensor;

import fr.cyu.rt.control.business.sensor.SensorType;

import java.time.LocalDateTime;

/**
 * @author Aldric Vitali Silvestre
 */
public record SensorDataMessage(
        String sensorId,
        SensorType sensorType,
        LocalDateTime timestamp,
        String value
) {
}
