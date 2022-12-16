package fr.cyu.rt.control.business.event;

/**
 * @author Aldric Vitali Silvestre
 */
public enum EventType {
    ALERT(true),
    ALERT_END(true),
    ALERT_DECISION(false),
    USER_ACTIVATION(false),
    USER_DEACTIVATION(false),
    USER_CONTROL(false),
    USER_CONTROL_END(false),
    LOST_CONNECTION(false)
    ;

    private boolean sentByHouse;

    EventType(boolean sentByHouse) {
        this.sentByHouse = sentByHouse;
    }

    public boolean isSentByHouse() {
        return sentByHouse;
    }
}
