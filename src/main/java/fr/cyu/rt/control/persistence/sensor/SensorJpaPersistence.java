package fr.cyu.rt.control.persistence.sensor;

import fr.cyu.rt.control.business.sensor.Sensor;
import fr.cyu.rt.control.business.sensor.SensorData;
import fr.cyu.rt.control.dao.sensor.SensorDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Aldric Vitali Silvestre
 */
// TODO implement the sensor persistence for real
//@Repository
public class SensorJpaPersistence implements SensorDao {
    @Override
    public Map<String, Sensor> getCurrentSensorStates() {
        return null;
    }

    @Override
    public List<SensorData> getSensorData(String sensorId, long durationS) {
        return null;
    }

    @Override
    public SensorData saveData(SensorData sensorData) {
        return null;
    }
}
