package fr.cyu.rt.control;

import fr.cyu.rt.control.api.ApiPackageMarker;
import fr.cyu.rt.control.business.BusinessPackageMarker;
import fr.cyu.rt.control.business.user.User;
import fr.cyu.rt.control.business.user.UserRole;
import fr.cyu.rt.control.config.ConfigPackageMarker;
import fr.cyu.rt.control.config.properties.PropertiesPackageMarker;
import fr.cyu.rt.control.dao.user.UserDao;
import fr.cyu.rt.control.persistence.PersistencePackageMarker;
import fr.cyu.rt.control.security.SecurityPackageMarker;
import fr.cyu.rt.control.services.ServicePackageMarker;
import fr.cyu.rt.control.services.user.ConnectedUserRegistry;
import fr.cyu.rt.control.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackageClasses = BusinessPackageMarker.class)
@ComponentScan(basePackageClasses = {
        ApiPackageMarker.class,
        ConfigPackageMarker.class,
        PersistencePackageMarker.class,
        ServicePackageMarker.class,
        SecurityPackageMarker.class
})
@ConfigurationPropertiesScan(basePackageClasses = PropertiesPackageMarker.class)
public class ControlApplication {

    //

    private static final Logger LOGGER = LoggerFactory.getLogger(ControlApplication.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ConnectedUserRegistry userRegistry;

    public static void main(String[] args) {
        SpringApplication.run(ControlApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            if (userDao.findAll().isEmpty()) {
                LOGGER.info("==== INIT DATABASE ====");
                userService.createUser("user",
                                       "123456",
                                       "bernard.carpette@yopmail.com",
                                       UserRole.USER
                );
                userService.createUser("admin",
                                       "123456",
                                       "billy@yopmail.com",
                                       UserRole.ADMIN
                );
                userService.createUser("com",
                                       "123456",
                                       "com@yopmail.com",
                                       UserRole.COM
                );
            }
            userRegistry.init();
        };
    }
}
