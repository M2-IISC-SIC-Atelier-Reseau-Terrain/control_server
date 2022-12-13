package fr.cyu.rt.control.api.rest.sensors;

import fr.cyu.rt.control.business.sensor.SensorType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author Aldric Vitali Silvestre
 */
@Controller
@RequestMapping("sensors")
public class SensorsController {

    @GetMapping("/")
    public ResponseEntity<RespSensorMultiple> getSensors() throws Exception {
        List<RespSensor> sensors = getRespSensors();
        return ResponseEntity.ok(new RespSensorMultiple(sensors));
    }

    private List<RespSensor> getRespSensors() {
        List<RespSensor> sensors = List.of(
                new RespSensor("camera1",
                               SensorType.CAMERA,
                               true,
                               false,
                               Map.of(
                                       "x_angle", 0.123,
                                       "y_angle", 0.456
                               )
                ),
                new RespSensor("distance1",
                               SensorType.DISTANCE,
                               true,
                               false,
                               Map.of(
                                       "value", 0.999
                               )
                ),
                new RespSensor("camera1",
                               SensorType.BUTTON,
                               true,
                               false,
                               Map.of(
                                       "value", 0
                               )
                )
        );
        return sensors;
    }
}
