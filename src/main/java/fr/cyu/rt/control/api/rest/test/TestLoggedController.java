package fr.cyu.rt.control.api.rest.test;

import fr.cyu.rt.control.business.user.User;
import fr.cyu.rt.control.services.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Aldric Vitali Silvestre
 */
@RestController
@RequestMapping("test")
public class TestLoggedController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> testLoggedUser() throws Exception {
        User user = authenticationService.retrieveUser();

        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "name", user.getName(),
                "lastLoginTime", user.getLastLoginTime()
        ));
    }

    @GetMapping("/error")
    public ResponseEntity<Map<String, Object>> testError() throws Exception {
        User user = authenticationService.retrieveUser();
        throw new IllegalArgumentException("I don't accept you, " + user.getName());
    }

    @GetMapping("/send")
    public ResponseEntity<String> testSendStomp() throws Exception {
        template.convertAndSend("/topic/messages", new TestSendStomp("", ""));

        return ResponseEntity.ok("Message Sended");
    }
    
    record TestSendStomp(
            String username,
            String message
    ) {
    }
}
