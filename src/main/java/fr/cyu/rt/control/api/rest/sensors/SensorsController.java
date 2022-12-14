package fr.cyu.rt.control.api.rest.sensors;

import fr.cyu.rt.control.api.rest.sensors.details.RespSensorDetails;
import fr.cyu.rt.control.services.sensor.SensorRegistry;
import fr.cyu.rt.control.services.sensor.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * @author Aldric Vitali Silvestre
 */
@Controller
@RequestMapping("sensors")
public class SensorsController {

    @Autowired
    private SensorRegistry sensorRegistry;

    @Autowired
    private SensorService sensorService;

    @GetMapping("/")
    public ResponseEntity<RespSensorMultiple> getSensors() throws Exception {
        return ResponseEntity.ok(new RespSensorMultiple(sensorRegistry.getSensors()));
    }

    @GetMapping("/{sensorId}/{type}")
    public ResponseEntity<RespSensorDetails> getSensorDetails(
            @PathVariable("sensorId") String sensorId,
            @PathVariable("type") String type,
            @RequestParam("duration") Optional<Long> optionalDurationS
    ) throws Exception {
        return ResponseEntity.ok(sensorService.getDetails(sensorId, type, optionalDurationS));
    }

    // TODO get sensor unique avec params
    // Besoin de créer les events d'abord
}
