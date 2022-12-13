package fr.cyu.rt.control.business.event;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Aldric Vitali Silvestre
 */
@Entity
public class Event {

    @Id
    @GeneratedValue
    private Long id;

    // TODO que met-on ?

    // TODO comment on stocke les images en fait ?
    // En vrai, juste une
}
