package fr.cyu.rt.control.business.sensor;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * One point of data for a sensor
 *
 * @author Aldric Vitali Silvestre
 */
public class SensorData {

    private String id;

    private SensorType type;

    private LocalDateTime receivedTimestamp;

    private LocalDateTime registeredTimestamp = LocalDateTime.now();

    private String value;

    public SensorData() {
    }

    public SensorData(String id,
                      SensorType type,
                      LocalDateTime receivedTimestamp,
                      LocalDateTime registeredTimestamp,
                      String value) {
        this.id = id;
        this.type = type;
        this.receivedTimestamp = receivedTimestamp;
        this.registeredTimestamp = registeredTimestamp;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SensorType getType() {
        return type;
    }

    public void setType(SensorType type) {
        this.type = type;
    }

    public LocalDateTime getReceivedTimestamp() {
        return receivedTimestamp;
    }

    public void setReceivedTimestamp(LocalDateTime receivedTimestamp) {
        this.receivedTimestamp = receivedTimestamp;
    }

    public LocalDateTime getRegisteredTimestamp() {
        return registeredTimestamp;
    }

    public void setRegisteredTimestamp(LocalDateTime registeredTimestamp) {
        this.registeredTimestamp = registeredTimestamp;
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
        SensorData that = (SensorData) o;
        return Objects.equals(id, that.id) && type == that.type && Objects.equals(receivedTimestamp,
                                                                                  that.receivedTimestamp
        ) && Objects.equals(registeredTimestamp, that.registeredTimestamp) && Objects.equals(value,
                                                                                             that.value
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, receivedTimestamp, registeredTimestamp, value);
    }
}
