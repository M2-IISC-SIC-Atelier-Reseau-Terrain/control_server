package fr.cyu.rt.control.services.event;

import fr.cyu.rt.control.api.rest.alert.AlertController;
import fr.cyu.rt.control.api.stomp.house.alert.HouseAlertMessageController;
import fr.cyu.rt.control.business.event.Event;
import fr.cyu.rt.control.business.event.EventType;
import fr.cyu.rt.control.business.event.types.*;
import fr.cyu.rt.control.business.sensor.SensorType;
import fr.cyu.rt.control.dao.event.EventDao;
import fr.cyu.rt.control.services.camera.CameraRegistry;
import fr.cyu.rt.control.services.camera.CameraStorage;
import fr.cyu.rt.control.services.sensor.SensorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Aldric Vitali Silvestre
 */
@Service
public class EventService {

    @Autowired
    private SensorRegistry sensorRegistry;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private CameraStorage cameraStorage;

    @Autowired
    private CameraRegistry cameraRegistry;

    /**
     * Store the alert event sent by the house. This does not handle the
     */
    public void onAlertReceived(HouseAlertMessageController.Alert alert) {
//        if (sensorRegistry.streamAlertedSensors().findAny().isEmpty()) {
//            throw new IllegalArgumentException("No sensor is alerted");
//        }
        EventType eventType = alert.alertType().getEventType();
        Event event = switch (eventType) {
            case ALERT -> createAlertEvent(alert);
            case ALERT_END -> createEndAlertEvent(alert);
            default -> throw new IllegalArgumentException("Invalid alert type");
        };
        eventDao.save(event);
    }

    private Event createAlertEvent(HouseAlertMessageController.Alert alert) {
        AlertStartEvent event = new AlertStartEvent(alert);
        event.setSensorId(alert.sensorId());
        // Store the image
        String imagePath = cameraStorage.registerLastImage();
        event.setImagePath(imagePath);
        event.setValueStr(alert.value().orElse(""));
        return event;
    }

    private Event createEndAlertEvent(HouseAlertMessageController.Alert alert) {
        AlertEndEvent event = new AlertEndEvent(alert);
        return eventDao.save(event);
    }

    public void onAlertDecision(AlertController.EndAlertRequest request) {
        AlertDecisionEvent alertDecisionEvent = new AlertDecisionEvent(request);
        sensorRegistry.removeAlertForSensors();
        eventDao.save(alertDecisionEvent);
    }

    public void onCameraControlStart() {
        cameraRegistry.setUserControlling(true);
        UserControlEvent event = new UserControlEvent();
        eventDao.save(event);
    }

    public void onCameraControlEnd() {
        cameraRegistry.setUserControlling(false);
        UserEndControlEvent event = new UserEndControlEvent();
        eventDao.save(event);
    }

    // TODO store other events (one method per event ?)
}
