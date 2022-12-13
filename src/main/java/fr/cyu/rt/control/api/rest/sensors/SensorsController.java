package fr.cyu.rt.control.api.rest.sensors;

import fr.cyu.rt.control.services.sensor.SensorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Aldric Vitali Silvestre
 */
@Controller
@RequestMapping("sensors")
public class SensorsController {

    @Autowired
    private SensorRegistry sensorRegistry;

    @GetMapping("/")
    public ResponseEntity<RespSensorMultiple> getSensors() throws Exception {
        return ResponseEntity.ok(new RespSensorMultiple(sensorRegistry.getSensors()));
    }

    // TODO get sensor unique avec params
    // Besoin de créer les events d'abord
}
