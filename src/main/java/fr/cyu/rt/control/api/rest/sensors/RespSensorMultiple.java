package fr.cyu.rt.control.api.rest.sensors;

import fr.cyu.rt.control.business.sensor.Sensor;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

/**
 * @author Aldric Vitali Silvestre
 */
public record RespSensorMultiple(
        LocalDateTime lastUpdate,
        List<RespSensor> sensors,
        boolean isAnyOnAlert
) {

    public RespSensorMultiple(List<Sensor> sensors) {
        this(sensors.stream()
                     .map(Sensor::getLastUpdateTime)
                     .max(Comparator.naturalOrder())
                     .get(),

             sensors.stream()
                     .map(RespSensor::new)
                     .toList(),

             sensors.stream()
                     .filter(Sensor::isOnAlert)
                     .findAny()
                     .isPresent()
        );
    }
}
