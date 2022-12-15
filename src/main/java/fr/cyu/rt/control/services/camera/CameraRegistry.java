package fr.cyu.rt.control.services.camera;

import org.springframework.stereotype.Component;

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

    private boolean isUserControlling = false;

    private byte[] imageBinary = null;

    public CameraRegistry() {
        imageBinary = new byte[640 * 480];
        new Random().nextBytes(imageBinary);
    }

    public synchronized byte[] getImageBinary() {
        return imageBinary;
    }

    public synchronized void setImageBinary(byte[] imageBinary) {
        this.imageBinary = imageBinary;
    }

    public synchronized boolean isUserControlling() {
        return isUserControlling;
    }

    public synchronized void setUserControlling(boolean userControlling) {
        isUserControlling = userControlling;
    }
}
