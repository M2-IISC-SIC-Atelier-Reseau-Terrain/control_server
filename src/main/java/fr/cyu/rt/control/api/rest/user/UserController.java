package fr.cyu.rt.control.api.rest.user;

import fr.cyu.rt.control.business.user.User;
import fr.cyu.rt.control.services.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aldric Vitali Silvestre
 */
@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/me")
    private ResponseEntity<RespUserMe> getMyInformation() throws Exception {
        User user = authenticationService.retrieveUser();
        return ResponseEntity.ok(new RespUserMe(user));
    }
}
