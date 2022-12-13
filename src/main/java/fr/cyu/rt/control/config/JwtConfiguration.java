package fr.cyu.rt.control.config;

import fr.cyu.rt.control.config.properties.JwtProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.security.KeyStore;

/**
 * @author Aldric Vitali Silvestre
 */
@Configuration
public class JwtConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtConfiguration.class);

    @Autowired
    private JwtProperties jwtProperties;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public KeyStore keyStore() {
        try {
            JwtProperties.Keystore keystore = jwtProperties.keystore();
            File keyStoreFile = new File(keystore.path());
            KeyStore keyStore = KeyStore.getInstance(keyStoreFile, keystore.keyStorePassword().toCharArray());
            return keyStore;
        }
        catch (Exception e) {
            LOGGER.error("Error while creating key store : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
