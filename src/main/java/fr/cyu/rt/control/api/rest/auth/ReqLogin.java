package fr.cyu.rt.control.api.rest.auth;

/**
 * @author Aldric Vitali Silvestre
 */
public record ReqLogin(
        String name,
        String password
) {
}
