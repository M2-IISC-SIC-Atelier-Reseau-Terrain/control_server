package fr.cyu.rt.control.api.rest.event;

import fr.cyu.rt.control.business.event.Event;

import java.util.List;

/**
 * @author Aldric Vitali Silvestre
 */
public class RespAllEvents {

    private List<RespOneEvent> events;

    public RespAllEvents(List<Event> events) {
        this.events = events.stream()
                .map(RespOneEvent::new)
                .toList();
    }
}
