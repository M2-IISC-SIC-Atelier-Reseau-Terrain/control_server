package fr.cyu.rt.control.api.rest.sensors.details;

import fr.cyu.rt.control.business.sensor.Sensor;
import fr.cyu.rt.control.services.sensor.SensorService;

import java.util.List;

/**
 * @author Aldric Vitali Silvestre
 */
public class RespSensorEventsDetails extends RespSensorDetails {

    private List<SensorService.Alert> alerts;

    private List<SensorService.Decision> decisions;

    private List<Object> activations;

    public RespSensorEventsDetails(Sensor sensor, List<SensorService.Alert> alerts, List<SensorService.Decision> decisions, List<Object> activations) {
        super(sensor);
        this.alerts = alerts;
        this.decisions = decisions;
        this.activations = activations;
    }

    public List<SensorService.Alert> getAlerts() {
        return alerts;
    }

    public List<SensorService.Decision> getDecisions() {
        return decisions;
    }

    public List<Object> getActivations() {
        return activations;
    }
}
