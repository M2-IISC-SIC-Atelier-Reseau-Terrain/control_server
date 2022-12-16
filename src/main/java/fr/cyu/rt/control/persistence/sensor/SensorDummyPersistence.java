package fr.cyu.rt.control.persistence.sensor;

import fr.cyu.rt.control.business.sensor.Sensor;
import fr.cyu.rt.control.business.sensor.SensorData;
import fr.cyu.rt.control.business.sensor.SensorType;
import fr.cyu.rt.control.dao.sensor.SensorDao;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Aldric Vitali Silvestre
 */
//@Repository
public class SensorDummyPersistence implements SensorDao {

    private static List<SensorData> sensorDataList = new ArrayList<>();

    private static Map<String, Sensor> sensors;

    static {
        Sensor cameraSensor = new Sensor("0",
                                         SensorType.CAMERA,
                                         true,
                                         false,
                                         LocalDateTime.now(),
                                         "1.0,1.0"
        );
        Sensor buttonSensor = new Sensor("1",
                                         SensorType.BUTTON,
                                         true,
                                         false,
                                         LocalDateTime.now().minusMinutes(2),
                                         "0"
        );
        Sensor distanceSensor = new Sensor("2",
                                           SensorType.DISTANCE,
                                           true,
                                           false,
                                           LocalDateTime.now().minusMinutes(5),
                                           "0.00014"
        );
        sensors = Map.of(
                "0", cameraSensor,
                "1", buttonSensor,
                "2", distanceSensor
        );
    }

    @Override
    public Map<String, Sensor> getCurrentSensorStates() {
        return sensors;
    }

    @Override
    public List<SensorData> getSensorData(String sensorId, long durationS) {
        return Collections.unmodifiableList(sensorDataList);
    }

    @Override
    public Optional<SensorData> findLastSensorDataOf(String sensorId) {
        return sensorDataList.stream()
                .filter(s -> s.getSensorId().equals(sensorId))
                .max(Comparator.comparing(SensorData::getReceivedTimestamp));
    }

    @Override
    public SensorData saveData(SensorData sensorData) {
        long lastId = 0;
        if (!sensorDataList.isEmpty()) {
            lastId = sensorDataList.get(sensorDataList.size() - 1).getId();
        }
        sensorData.setId(lastId + 1);
        sensorDataList.add(sensorData);
        return sensorData;
    }
}
