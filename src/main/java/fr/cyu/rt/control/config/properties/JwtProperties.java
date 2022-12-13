package fr.cyu.rt.control.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * @author Aldric Vitali Silvestre
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String header,
        long expiration,
        String signatureAlgorithm,
        Keystore keystore
) {

    public record Keystore(
            String path,
            String alias,
            String keyStorePassword,
            String aliasPassword
    ){
    }
}
