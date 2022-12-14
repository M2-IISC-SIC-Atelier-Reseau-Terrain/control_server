package fr.cyu.rt.control.business.event.types;

import fr.cyu.rt.control.business.event.Event;
import fr.cyu.rt.control.business.event.EventType;

/**
 * @author Aldric Vitali Silvestre
 */
public class UserEndControlEvent extends Event {

    public UserEndControlEvent() {
        super(EventType.USER_END_CONTROL);
    }

}
