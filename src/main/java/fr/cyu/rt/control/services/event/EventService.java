package fr.cyu.rt.control.services.event;

import fr.cyu.rt.control.api.rest.alert.AlertController;
import fr.cyu.rt.control.api.stomp.house.alert.HouseAlertMessageController;
import fr.cyu.rt.control.business.event.Event;
import fr.cyu.rt.control.business.event.EventType;
import fr.cyu.rt.control.business.event.types.*;
import fr.cyu.rt.control.business.sensor.Sensor;
import fr.cyu.rt.control.business.sensor.SensorType;
import fr.cyu.rt.control.dao.event.EventDao;
import fr.cyu.rt.control.services.camera.CameraRegistry;
import fr.cyu.rt.control.services.camera.CameraStorage;
import fr.cyu.rt.control.services.sensor.SensorRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

/**
 * @author Aldric Vitali Silvestre
 */
@Service
public class EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);

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
    @Transactional
    public void onAlertReceived(HouseAlertMessageController.Alert alert) {
        LOGGER.info("Alert received : {} {}", alert.sensorType(), alert.value().orElse(""));
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

    @Transactional
    private Event createAlertEvent(HouseAlertMessageController.Alert alert) {
        AlertStartEvent event = new AlertStartEvent(alert);
        event.setSensorId(alert.sensorId());
        Sensor sensor = sensorRegistry.getSensor(alert.sensorId())
                .orElseThrow();
        sensor.setLastUpdateTime(LocalDateTime.now());
        sensor.setOnAlert(true);
        // Store the image
        String imagePath = cameraStorage.registerLastImage();
        event.setImagePath(imagePath);
        event.setValueStr(alert.value().orElse(""));
        return event;
    }

    @Transactional
    private Event createEndAlertEvent(HouseAlertMessageController.Alert alert) {
        AlertEndEvent event = new AlertEndEvent(alert);
        return eventDao.save(event);
    }

    @Transactional
    public void onAlertDecision(AlertController.EndAlertRequest request) {
        LOGGER.info("Alert decision received. Real alert : {}", request.realAlert());
        AlertDecisionEvent alertDecisionEvent = new AlertDecisionEvent(request);
        sensorRegistry.removeAlertForSensors();
        eventDao.save(alertDecisionEvent);
    }

    @Transactional
    public void onCameraControlStart() {
        LOGGER.info("Start camera control");
        cameraRegistry.setUserControlling(true);
        UserControlEvent event = new UserControlEvent();
        eventDao.save(event);
    }

    @Transactional
    public void onCameraControlEnd() {
        LOGGER.info("End camera control");
        cameraRegistry.setUserControlling(false);
        UserEndControlEvent event = new UserEndControlEvent();
        eventDao.save(event);
    }

    // TODO store other events (one method per event ?)
}
