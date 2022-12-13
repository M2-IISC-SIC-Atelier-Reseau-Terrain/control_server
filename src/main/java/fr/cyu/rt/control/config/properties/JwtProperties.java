package fr.cyu.rt.control.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.nio.file.Path;

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
            Path path,
            String alias,
            String keyStorePassword,
            String aliasPassword
    ){
    }
}
