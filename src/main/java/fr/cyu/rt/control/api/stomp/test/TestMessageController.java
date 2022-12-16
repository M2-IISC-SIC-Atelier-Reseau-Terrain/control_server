package fr.cyu.rt.control.api.stomp.test;

import fr.cyu.rt.control.business.user.User;
import fr.cyu.rt.control.security.JwtUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDateTime;

/**
 * @author Aldric Vitali Silvestre
 */
@Controller
public class  TestMessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestMessageController.class);

    @MessageMapping("/test")
    @SendTo("/topic/tests")
    public TestRespMessage handleTestMessage(TestReqMessage message, SimpMessageHeaderAccessor headerAccessor)
            throws Exception {

        LOGGER.trace("==== Received the test message ====");

        Thread.sleep(1000);
        UsernamePasswordAuthenticationToken userAuthentication = (UsernamePasswordAuthenticationToken) headerAccessor.getUser();

        JwtUserDetails userDetails = (JwtUserDetails) userAuthentication.getPrincipal();
        User user = userDetails.getUser();

        return new TestRespMessage(
                user.getName(),
                HtmlUtils.htmlEscape(message.content()),
                LocalDateTime.now()
        );
    }

    @MessageMapping("/test/binary")
    public void receiveCameraImage(TestReqMessage message) throws Exception {
        LOGGER.debug("Camera image received");
        String payload = message.content();
        System.out.println("Received byte array of " + payload);
    }


    record TestReqMessage(
            String content
    ) {
    }

    record TestRespMessage(
            String username,
            String content,
            LocalDateTime timestamp
    ) {
    }
}
