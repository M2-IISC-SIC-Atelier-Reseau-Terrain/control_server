package fr.cyu.rt.control.dao.sensor;

import fr.cyu.rt.control.business.sensor.Sensor;

import java.util.Map;

/**
 * @author Aldric Vitali Silvestre
 */
public interface SensorDao {

    /**
     * Find the current sensor states from the information
     * on the persistence layer.
     * @return
     */
    Map<String, Sensor> getCurrentSensorStates();

}
