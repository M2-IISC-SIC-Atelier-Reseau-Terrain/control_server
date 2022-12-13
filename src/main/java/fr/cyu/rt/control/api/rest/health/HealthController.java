package fr.cyu.rt.control.api.rest.health;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aldric Vitali Silvestre
 */
@RestController
@RequestMapping("health")
public class HealthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthController.class);

    @GetMapping("/")
    public String ping() {
        LOGGER.trace("==== RECEIVE HEALTH REQUEST ====");
        return "pong";
    }

}
