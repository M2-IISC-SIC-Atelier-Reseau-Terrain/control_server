package fr.cyu.rt.control.api.rest.event;

import fr.cyu.rt.control.business.event.Event;
import fr.cyu.rt.control.dao.event.EventDao;
import fr.cyu.rt.control.services.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Aldric Vitali Silvestre
 */
@RestController
@RequestMapping("events")
public class EventController {

    @Autowired
    private EventDao eventDao;

    @GetMapping("/")
    public ResponseEntity<RespAllEvents> getEvents() throws Exception {
        List<Event> events = eventDao.getAll();
        return ResponseEntity.ok(new RespAllEvents(events));
    }
}
