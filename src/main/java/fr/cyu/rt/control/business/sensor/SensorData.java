package fr.cyu.rt.control.business.sensor;

import org.springframework.stereotype.Indexed;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * One point of data for a sensor
 *
 * @author Aldric Vitali Silvestre
 */
@Entity
@Table(indexes = {
        @Index(name = "SENSOR_IDX_TIMESTAMP", columnList = "received_timestamp")
})
public class SensorData {

    @Id
    @GeneratedValue
    private Long id;

    private String sensorId;

    @Column(name = "received_timestamp", nullable = false)
    private LocalDateTime receivedTimestamp;

    private SensorType type;

    private LocalDateTime registeredTimestamp = LocalDateTime.now();

    private String value;

    public SensorData() {
    }

    public SensorData(String sensorId,
                      SensorType type,
                      LocalDateTime receivedTimestamp,
                      LocalDateTime registeredTimestamp,
                      String value) {
        this.sensorId = sensorId;
        this.type = type;
        this.receivedTimestamp = receivedTimestamp;
        this.registeredTimestamp = registeredTimestamp;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
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
        return Objects.equals(id, that.id) && Objects.equals(sensorId,
                                                             that.sensorId
        ) && Objects.equals(receivedTimestamp,
                            that.receivedTimestamp
        ) && type == that.type && Objects.equals(registeredTimestamp,
                                                 that.registeredTimestamp
        ) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sensorId, receivedTimestamp, type, registeredTimestamp, value);
    }
}
