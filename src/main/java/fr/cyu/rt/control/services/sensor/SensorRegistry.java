package fr.cyu.rt.control.services.sensor;

import fr.cyu.rt.control.business.sensor.Sensor;
import fr.cyu.rt.control.dao.sensor.SensorDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A component that will keep track of the current sensor's state.
 *
 * When starting the server, the sensor states will be taken from the
 * dao layer, alerts included.
 *
 * @author Aldric Vitali Silvestre
 */
@Component
public class SensorRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(SensorRegistry.class);

    @Autowired
    private SensorDao sensorDao;

    private Map<String, Sensor> sensorById;
    private List<Sensor> sensors;

    @PostConstruct
    public void initSensorStates() {
        LOGGER.info("==== GET SENSOR STATES ====");
        sensorById = sensorDao.getCurrentSensorStates();
        sensors = sensorById.values().stream().toList();
    }

    public Optional<Sensor> getSensor(String sensorId) {
        LOGGER.trace("Try getting sensor with id {}", sensorId);
        return Optional.ofNullable(sensorById.get(sensorId));
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public Stream<Sensor> streamAlertedSensors() {
        return sensors.stream()
                .filter(Sensor::isOnAlert);
    }
}
