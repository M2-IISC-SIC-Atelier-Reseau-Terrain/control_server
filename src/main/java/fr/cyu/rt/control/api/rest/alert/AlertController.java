package fr.cyu.rt.control.api.rest.alert;

import fr.cyu.rt.control.services.event.EventService;
import fr.cyu.rt.control.services.user.ConnectedUserRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

/**
 * @author Aldric Vitali Silvestre
 */
@Controller
@RequestMapping("alerts")
public class AlertController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ConnectedUserRegistry userRegistry;

    @Autowired
    private SimpMessagingTemplate template;

    @PostMapping("end")
    public ResponseEntity<Void> endAlert(@RequestBody EndAlertRequest request) throws Exception {
        // register the response
        eventService.onAlertDecision(request);
        // Send the request to the house
        if (userRegistry.getHouseUserState().isConnected()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .build();
        } else {
            template.convertAndSend("/topic/alert", new EndAlertHouseMessage(request));
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .build();
    }

    public record EndAlertRequest(
            boolean realAlert
    ) {
    }

    public record EndAlertHouseMessage(
        boolean realAlert,
        LocalDateTime timestamp
    ){
        public EndAlertHouseMessage(EndAlertRequest request) {
            this(request.realAlert(), LocalDateTime.now());
        }
    }
}
