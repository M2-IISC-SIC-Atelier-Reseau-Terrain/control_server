package fr.cyu.rt.control.services.camera;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Random;

/**
 * A component that will keep the last camera image received.
 *
 * When starting the server, no picture will be available.
 *
 * @author Aldric Vitali Silvestre
 */
@Component
public class CameraRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(CameraRegistry.class);

    private static final int DATA_LEN = 640 * 480;

    private boolean isUserControlling = false;

    private byte[] imageBinary = null;

    private LocalDateTime lastImageReceived;

    public CameraRegistry() {
        imageBinary = new byte[640 * 480];
        new Random().nextBytes(imageBinary);
    }

    public byte[] updateFromBase64(String base64Str) {
        byte[] decoded = Base64.getDecoder().decode(base64Str);
        setImageBinary(decoded);
        return decoded;
    }

    public synchronized byte[] getImageBinary() {
        return imageBinary;
    }

    public synchronized void setImageBinary(byte[] imageBinary) {
        if (imageBinary.length != DATA_LEN)
        {
            LOGGER.warn("Image length is not {} (got {}), don't register", DATA_LEN, imageBinary.length);
        }
        this.imageBinary = imageBinary;
        lastImageReceived = LocalDateTime.now();
    }

    public synchronized boolean isUserControlling() {
        return isUserControlling;
    }

    public synchronized void setUserControlling(boolean userControlling) {
        isUserControlling = userControlling;
    }
}
