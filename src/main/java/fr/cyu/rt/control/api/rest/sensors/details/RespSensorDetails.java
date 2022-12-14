package fr.cyu.rt.control.api.rest.sensors.details;

import fr.cyu.rt.control.business.sensor.Sensor;
import fr.cyu.rt.control.business.sensor.SensorType;

/**
 * @author Aldric Vitali Silvestre
 */
public abstract class RespSensorDetails {

    private String id;

    private SensorType type;

    public RespSensorDetails(Sensor sensor) {
        this.id = sensor.getId();
        this.type = sensor.getType();
    }

    public String getId() {
        return id;
    }

    public SensorType getType() {
        return type;
    }
}
