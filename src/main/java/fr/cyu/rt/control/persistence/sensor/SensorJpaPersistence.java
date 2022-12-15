package fr.cyu.rt.control.persistence.sensor;

import fr.cyu.rt.control.business.sensor.QSensorData;
import fr.cyu.rt.control.business.sensor.Sensor;
import fr.cyu.rt.control.business.sensor.SensorData;
import fr.cyu.rt.control.business.sensor.SensorType;
import fr.cyu.rt.control.dao.event.EventDao;
import fr.cyu.rt.control.dao.sensor.SensorDao;
import fr.cyu.rt.control.persistence.jpa.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Aldric Vitali Silvestre
 */
// TODO implement the sensor persistence for real
//@Repository
public class SensorJpaPersistence extends BaseJpaRepository<SensorData, Long> implements SensorDao {

    @Autowired
    private EventDao eventDao;

    private static QSensorData SENSOR_DATA = QSensorData.sensorData;

    public SensorJpaPersistence(EntityManager em) {
        super(SensorData.class, em);
    }

    @Override
    public List<SensorData> getSensorData(String sensorId, long durationS) {
        LocalDateTime target = LocalDateTime.now().minusSeconds(durationS);
        return queryDslFactory()
                .selectFrom(SENSOR_DATA)
                .where(SENSOR_DATA.sensorId.eq(sensorId),
                       SENSOR_DATA.receivedTimestamp.after(target))
                .orderBy(SENSOR_DATA.receivedTimestamp.asc())
                .fetch();
    }

    @Override
    public Optional<SensorData> findLastSensorDataOf(String sensorId) {
        return queryDslFactory()
                .selectFrom(SENSOR_DATA)
                .where(SENSOR_DATA.sensorId.eq(sensorId))
                .orderBy(SENSOR_DATA.receivedTimestamp.desc())
                .stream().findFirst();
    }

    @Override
    public SensorData saveData(SensorData sensorData) {
        return saveEntity(sensorData);
    }

    @Override
    public Map<String, Sensor> getCurrentSensorStates() {
        Sensor cameraSensor = new Sensor("0",
                                         SensorType.CAMERA,
                                         true,
                                         false,
                                         LocalDateTime.now(),
                                         "0.0,0.0"
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
                                           "0.0"
        );
        // Update from last entry on database
        updateSensor(cameraSensor);
        updateSensor(buttonSensor);
        updateSensor(distanceSensor);
        return Map.of(
                "0", cameraSensor,
                "1", buttonSensor,
                "2", distanceSensor
        );
    }


    private void updateSensor(Sensor sensor) {
        findLastSensorDataOf(sensor.getId())
                .ifPresent(data -> {
                    sensor.setValue(data.getValueStr());
                    sensor.setLastUpdateTime(data.getReceivedTimestamp());
                });

        sensor.setOnAlert(eventDao.isSensorAlerted(sensor.getId()));
    }
}
