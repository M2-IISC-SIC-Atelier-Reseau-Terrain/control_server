package fr.cyu.rt.control.api.rest.event;

import fr.cyu.rt.control.business.event.Event;
import fr.cyu.rt.control.business.event.EventType;

import java.time.LocalDateTime;

/**
 * @author Aldric Vitali Silvestre
 */
public class RespOneEvent {

    private EventType eventType;

    private String sensorId;

    private LocalDateTime timestamp;

    private String value;

    public RespOneEvent(Event event) {
        this.eventType = event.getEventType();
        this.sensorId = event.getSensorId();
        this.timestamp = event.getTimestampReceived();
        this.value = event.getValueStr();
    }

    public EventType getEventType() {
        return eventType;
    }

    public String getSensorId() {
        return sensorId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getValue() {
        return value;
    }
}
