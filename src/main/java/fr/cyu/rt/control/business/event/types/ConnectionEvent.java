package fr.cyu.rt.control.business.event.types;

import fr.cyu.rt.control.business.event.Event;
import fr.cyu.rt.control.business.event.EventType;
import fr.cyu.rt.control.business.user.UserRole;

import javax.persistence.Entity;
import java.util.Objects;

/**
 * @author Aldric Vitali Silvestre
 */
@Entity
public class ConnectionEvent extends Event {

    private UserRole role;

    private boolean connected;

    public ConnectionEvent() {
        super(EventType.LOST_CONNECTION);
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
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
        ConnectionEvent that = (ConnectionEvent) o;
        return connected == that.connected && role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), role, connected);
    }
}
