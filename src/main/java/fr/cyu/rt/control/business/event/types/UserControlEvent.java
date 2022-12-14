package fr.cyu.rt.control.business.event.types;

import fr.cyu.rt.control.business.event.Event;
import fr.cyu.rt.control.business.event.EventType;

/**
 * @author Aldric Vitali Silvestre
 */
public class UserControlEvent extends Event {

    public UserControlEvent() {
        super(EventType.USER_CONTROL);
    }
}
