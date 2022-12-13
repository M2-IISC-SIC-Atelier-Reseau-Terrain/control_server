package fr.cyu.rt.control.business.sensor;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The current data of one sensor.
 *
 * This is not intended to be a persisted data
 *
 * @author Aldric Vitali Silvestre
 */
public class Sensor {

    private String id;

    private SensorType type;

    private boolean isActive;

    private boolean isOnAlert;

    private LocalDateTime lastUpdateTime;

    private String value;

    public Sensor() {
    }

    public Sensor(String id,
                  SensorType type,
                  boolean isActive,
                  boolean isOnAlert,
                  LocalDateTime lastUpdateTime,
                  String value) {
        this.id = id;
        this.type = type;
        this.isActive = isActive;
        this.isOnAlert = isOnAlert;
        this.lastUpdateTime = lastUpdateTime;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isOnAlert() {
        return isOnAlert;
    }

    public void setOnAlert(boolean onAlert) {
        isOnAlert = onAlert;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
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
        Sensor sensor = (Sensor) o;
        return isActive == sensor.isActive && isOnAlert == sensor.isOnAlert && Objects.equals(id,
                                                                                              sensor.id
        ) && type == sensor.type && Objects.equals(lastUpdateTime,
                                                   sensor.lastUpdateTime
        ) && Objects.equals(value, sensor.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, isActive, isOnAlert, lastUpdateTime, value);
    }
}
