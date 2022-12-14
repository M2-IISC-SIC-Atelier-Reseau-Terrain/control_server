package fr.cyu.rt.control.services.camera;

import org.springframework.stereotype.Component;

/**
 * A component that will keep the last camera image received.
 *
 * When starting the server, no picture will be available.
 *
 * @author Aldric Vitali Silvestre
 */
@Component
public class CameraRegistry {

    private byte[] imageBinary = null;

    public byte[] getImageBinary() {
        return imageBinary;
    }

    public void setImageBinary(byte[] imageBinary) {
        this.imageBinary = imageBinary;
    }
}
