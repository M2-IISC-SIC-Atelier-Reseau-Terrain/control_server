package fr.cyu.rt.control.services.sensor;

import fr.cyu.rt.control.api.rest.sensors.details.RespSensorDetails;
import fr.cyu.rt.control.api.rest.sensors.details.RespSensorEventsDetails;
import fr.cyu.rt.control.api.rest.sensors.details.RespSensorValuesDetails;
import fr.cyu.rt.control.business.event.Event;
import fr.cyu.rt.control.business.event.EventType;
import fr.cyu.rt.control.business.event.types.AlertDecisionEvent;
import fr.cyu.rt.control.business.sensor.Sensor;
import fr.cyu.rt.control.business.sensor.SensorData;
import fr.cyu.rt.control.dao.event.EventDao;
import fr.cyu.rt.control.dao.sensor.SensorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Aldric Vitali Silvestre
 */
@Service
public class SensorService {

    @Autowired
    private SensorRegistry sensorRegistry;

    @Autowired
    private SensorDao sensorDao;

    @Autowired
    private EventDao eventDao;

    public RespSensorDetails getDetails(String sensorId, String type, Optional<Long> optionalDurationS) {
        long durationS = optionalDurationS.orElse(5l);
        switch (type) {
            case "VALUES":
                return getValueDetails(sensorId, durationS);
            case "EVENTS":
                return getEventsDetails(sensorId, durationS);
        }
        throw new IllegalArgumentException("Type is not recognized: " + type);
    }

    private RespSensorValuesDetails getValueDetails(String sensorId, long durationS) {
        Sensor sensor = getSensorFromRegistry(sensorId);
        List<SensorData> sensorData = sensorDao.getSensorData(sensorId, durationS);
        return new RespSensorValuesDetails(sensor, sensorData);
    }

    private Sensor getSensorFromRegistry(String sensorId) {
        return sensorRegistry.getSensor(sensorId)
                .orElseThrow(() -> new NoSuchElementException("Cannot find the sensor by id"));
    }

    private RespSensorEventsDetails getEventsDetails(String sensorId, long durationS) {
        Sensor sensor = getSensorFromRegistry(sensorId);
        List<Event> eventsForSensor = eventDao.getEventsForSensor(sensorId, durationS);
        // split by alerts, decisions, activation
        List<Alert> alerts = eventsForSensor.stream()
                .filter(this::isAlertEvent)
                .map(Alert::new)
                .toList();
        List<Decision> decisions = eventsForSensor.stream()
                .map(this::toDecisionEvent)
                .flatMap(Optional::stream)
                .map(Decision::new)
                .toList();
        return new RespSensorEventsDetails(sensor, alerts, decisions, List.of());
    }

    private boolean isAlertEvent(Event event) {
        EventType eventType = event.getEventType();
        return eventType == EventType.ALERT
                || eventType == EventType.ALERT_END;
    }

    public record Alert(
            LocalDateTime timestamp,
            boolean detected
    ) {

        public Alert(Event event) {
            this(
                event.getTimestampReceived(),
                event.getEventType() == EventType.ALERT
            );
        }
    }

    private Optional<AlertDecisionEvent> toDecisionEvent(Event event) {
        if (event instanceof AlertDecisionEvent alertDecisionEvent) {
            return Optional.of(alertDecisionEvent);
        }
        return Optional.empty();
    }

    public record Decision(
            LocalDateTime timestamp,
            boolean realAlert
    ) {
        public Decision(AlertDecisionEvent event) {
            this(event.getTimestampReceived(),
                 event.isRealAlert()
            );
        }
    }
}
