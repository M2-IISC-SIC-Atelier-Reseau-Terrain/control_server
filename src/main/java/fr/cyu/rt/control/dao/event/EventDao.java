package fr.cyu.rt.control.dao.event;

import fr.cyu.rt.control.business.event.Event;

import java.util.List;

/**
 * @author Aldric Vitali Silvestre
 */
public interface EventDao {

    List<Event> getEventsForSensor(String sensorId, long durationS);

    List<Event> getAll();

    Event save(Event event);
}
