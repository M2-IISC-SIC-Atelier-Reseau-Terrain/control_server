package fr.cyu.rt.control.business.event;

import fr.cyu.rt.control.business.sensor.SensorType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Aldric Vitali Silvestre
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Event {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private String sensorId;

    @Enumerated(EnumType.STRING)
    private SensorType sensorType;

    private LocalDateTime timestampReceived;

    private LocalDateTime timestampStored = LocalDateTime.now();

    private String value;

    public Event() {
    }

    public Event(Long id,
                 EventType eventType,
                 String sensorId,
                 SensorType sensorType,
                 LocalDateTime timestampReceived, LocalDateTime timestampStored, String value) {
        this.id = id;
        this.eventType = eventType;
        this.sensorId = sensorId;
        this.sensorType = sensorType;
        this.timestampReceived = timestampReceived;
        this.timestampStored = timestampStored;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public SensorType getSensorType() {
        return sensorType;
    }

    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    public LocalDateTime getTimestampReceived() {
        return timestampReceived;
    }

    public void setTimestampReceived(LocalDateTime timestampReceived) {
        this.timestampReceived = timestampReceived;
    }

    public LocalDateTime getTimestampStored() {
        return timestampStored;
    }

    public void setTimestampStored(LocalDateTime timestampStored) {
        this.timestampStored = timestampStored;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        return Objects.equals(id, event.id) && eventType == event.eventType && Objects.equals(sensorId,
                                                                                              event.sensorId
        ) && sensorType == event.sensorType && Objects.equals(timestampReceived,
                                                              event.timestampReceived
        ) && Objects.equals(timestampStored, event.timestampStored) && Objects.equals(value,
                                                                                      event.value
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventType, sensorId, sensorType, timestampReceived, timestampStored, value);
    }
}
