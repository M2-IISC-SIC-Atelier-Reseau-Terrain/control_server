package fr.cyu.rt.control.api.rest.sensors;

import fr.cyu.rt.control.business.sensor.SensorType;

import java.util.Map;

/**
 * @author Aldric Vitali Silvestre
 */
public record RespSensor(
        String sensorId,
        SensorType type,
        boolean active,
        boolean alert,
        Map<String, Object> values
) {

}
