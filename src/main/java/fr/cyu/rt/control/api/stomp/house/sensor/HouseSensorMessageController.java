package fr.cyu.rt.control.api.stomp.house.sensor;

import fr.cyu.rt.control.services.sensor.SensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 * @author Aldric Vitali Silvestre
 */
@Controller
public class HouseSensorMessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseSensorMessageController.class);

    @Autowired
    private SensorService sensorService;

    @MessageMapping("/house/sensor")
    public void receiveSensorData(SensorDataMessage message) throws Exception {
        LOGGER.trace("Receive sensor message");
        sensorService.onDataReceived(message);
    }
}
