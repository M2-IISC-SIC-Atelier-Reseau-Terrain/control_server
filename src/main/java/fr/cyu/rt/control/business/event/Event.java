package fr.cyu.rt.control.business.event;

import fr.cyu.rt.control.business.sensor.SensorType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Aldric Vitali Silvestre
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private String sensorId = "";

    @Enumerated(EnumType.STRING)
    private SensorType sensorType = SensorType.NONE;

    private LocalDateTime timestampReceived = LocalDateTime.now();

    private LocalDateTime timestampStored = LocalDateTime.now();

    private String valueStr = "";

    public Event() {
    }

    public Event(EventType eventType) {
        this.eventType = eventType;
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

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
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
        ) && Objects.equals(timestampStored, event.timestampStored) && Objects.equals(valueStr,
                                                                                      event.valueStr
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventType, sensorId, sensorType, timestampReceived, timestampStored, valueStr);
    }
}
