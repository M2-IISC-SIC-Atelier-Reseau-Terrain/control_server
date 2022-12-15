package fr.cyu.rt.control.dao.sensor;

import fr.cyu.rt.control.business.sensor.Sensor;
import fr.cyu.rt.control.business.sensor.SensorData;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    /**
     * Fetch all values of the sensor
     *
     * @param sensorId
     * @param durationS
     * @return
     */
    List<SensorData> getSensorData(String sensorId, long durationS);

    Optional<SensorData> findLastSensorDataOf(String sensorId);

    SensorData saveData(SensorData sensorData);
}
