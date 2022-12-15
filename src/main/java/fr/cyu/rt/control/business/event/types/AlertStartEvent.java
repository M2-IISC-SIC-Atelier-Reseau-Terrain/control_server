package fr.cyu.rt.control.business.event.types;

import fr.cyu.rt.control.api.stomp.house.alert.HouseAlertMessageController;
import fr.cyu.rt.control.business.event.Event;
import fr.cyu.rt.control.business.event.EventType;

import javax.persistence.Entity;
import java.util.Objects;

/**
 * @author Aldric Vitali Silvestre
 */
@Entity
public class AlertStartEvent extends Event {

    private String imagePath;

    public AlertStartEvent(String imagePath) {
        super(EventType.ALERT);
    }

    public AlertStartEvent() {
        this("");
    }

    public AlertStartEvent(HouseAlertMessageController.Alert alert) {
        this("");
        setSensorId(alert.sensorId());
        setSensorType(alert.sensorType());
        setTimestampReceived(alert.timestamp());
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        AlertStartEvent that = (AlertStartEvent) o;
        return Objects.equals(imagePath, that.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), imagePath);
    }
}
