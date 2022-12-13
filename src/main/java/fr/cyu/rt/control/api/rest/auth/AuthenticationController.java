package fr.cyu.rt.control.api.rest.auth;

import fr.cyu.rt.control.services.auth.AuthenticationService;
import fr.cyu.rt.control.services.auth.JwtTokenService;
import fr.cyu.rt.control.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Aldric Vitali Silvestre
 */
@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtTokenService tokenService;

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public ResponseEntity<RespToken> login(@RequestBody ReqLogin request) throws Exception {
        String token = authenticationService.createToken(request);
        userService.onLogin(request.name());
        return ResponseEntity.ok(new RespToken(token));
    }

    @GetMapping("refresh")
    public ResponseEntity<RespToken> refreshToken(HttpServletRequest request) throws Exception {
        LOGGER.trace("Refresh token");
        String token = authenticationService.refreshToken(tokenService.getTokenHeader(request));
        return ResponseEntity.ok(new RespToken(token));
    }
}
