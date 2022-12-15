package fr.cyu.rt.control.business.event.types;

import fr.cyu.rt.control.business.event.Event;
import fr.cyu.rt.control.business.event.EventType;

import javax.persistence.Entity;

/**
 * @author Aldric Vitali Silvestre
 */
@Entity
public class UserDeactivationEvent extends Event {

    public UserDeactivationEvent() {
        super(EventType.USER_DEACTIVATION);
    }
}
