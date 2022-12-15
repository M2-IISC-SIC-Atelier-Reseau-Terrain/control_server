package fr.cyu.rt.control.api.rest.sensors;

import fr.cyu.rt.control.business.sensor.Sensor;
import fr.cyu.rt.control.business.sensor.SensorType;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Aldric Vitali Silvestre
 */
public record RespSensor(
        String sensorId,
        SensorType type,
        boolean active,
        boolean alert,
        LocalDateTime lastUpdate,
        String value
) {

    public RespSensor(Sensor sensor) {
        this(sensor.getId(),
             sensor.getType(),
             sensor.isActive(),
             sensor.isOnAlert(),
             sensor.getLastUpdateTime(),
             // TODO better handle when we know how we have those values
             sensor.getValue()
        );
    }
}
