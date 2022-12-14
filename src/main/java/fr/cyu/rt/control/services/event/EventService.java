package fr.cyu.rt.control.services.event;

import fr.cyu.rt.control.api.stomp.house.alert.HouseAlertMessageController;
import org.springframework.stereotype.Service;

/**
 * @author Aldric Vitali Silvestre
 */
@Service
public class EventService {

    public void onAlertReceived(HouseAlertMessageController.Alert alert) {
        // TODO store alert, do other things based on type
    }

    // TODO store other events (one method per event ?)
}
