package fr.cyu.rt.control.api.stomp.house;

import fr.cyu.rt.control.business.event.EventType;

import java.time.LocalDateTime;

/**
 * @author Aldric Vitali Silvestre
 */
public record EventMessage(
        EventType type,
        LocalDateTime timestamp,
        String value
) {

    public EventMessage(EventType type) {
        this(type, LocalDateTime.now(), "");
    }

    public EventMessage(EventType type, String value) {
        this(type, LocalDateTime.now(), value);
    }
}
