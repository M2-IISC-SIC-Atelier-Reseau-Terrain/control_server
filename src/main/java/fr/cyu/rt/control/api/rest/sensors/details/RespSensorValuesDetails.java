package fr.cyu.rt.control.api.rest.sensors.details;

import fr.cyu.rt.control.business.sensor.Sensor;
import fr.cyu.rt.control.business.sensor.SensorData;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Aldric Vitali Silvestre
 */
public class RespSensorValuesDetails extends RespSensorDetails {

    private List<Data> values;

    public RespSensorValuesDetails(Sensor sensor, List<SensorData> sensorData) {
        super(sensor);
        values = sensorData.stream()
                .map(Data::new)
                .toList();
    }

    public List<Data> getValues() {
        return values;
    }

    record Data(
            LocalDateTime timestamp,
            String value
    ){
        public Data(SensorData sensorData) {
            this(sensorData.getReceivedTimestamp(), sensorData.getValue());
        }
    }
}
