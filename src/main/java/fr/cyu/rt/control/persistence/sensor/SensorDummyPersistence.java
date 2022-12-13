package fr.cyu.rt.control.persistence.sensor;

import fr.cyu.rt.control.business.sensor.Sensor;
import fr.cyu.rt.control.business.sensor.SensorType;
import fr.cyu.rt.control.dao.sensor.SensorDao;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Aldric Vitali Silvestre
 */
@Repository
public class SensorDummyPersistence implements SensorDao {

    @Override
    public Map<String, Sensor> getCurrentSensorStates() {
        Sensor cameraSensor = new Sensor("camera1",
                                         SensorType.CAMERA,
                                         true,
                                         false,
                                         LocalDateTime.now(),
                                         "1.0,1.0"
        );
        Sensor buttonSensor = new Sensor("button1",
                                         SensorType.BUTTON,
                                         true,
                                         false,
                                         LocalDateTime.now().minusMinutes(2),
                                         "0"
        );
        Sensor distanceSensor = new Sensor("distance1",
                                         SensorType.DISTANCE,
                                         true,
                                         false,
                                         LocalDateTime.now().minusMinutes(5),
                                         "0.00014"
        );
        return Map.of(
                "camera1", cameraSensor,
                "button1", buttonSensor,
                "distance1", distanceSensor
        );
    }
}
