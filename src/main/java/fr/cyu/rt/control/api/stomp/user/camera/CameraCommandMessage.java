package fr.cyu.rt.control.api.stomp.user.camera;

/**
 * @author Aldric Vitali Silvestre
 */
public record CameraCommandMessage(
        float angleX,
        float angleY
) {
}
