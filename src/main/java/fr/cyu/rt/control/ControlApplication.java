package fr.cyu.rt.control;

import fr.cyu.rt.control.api.ApiPackageMarker;
import fr.cyu.rt.control.business.BusinessPackageMarker;
import fr.cyu.rt.control.config.ConfigPackageMarker;
import fr.cyu.rt.control.config.properties.PropertiesPackageMarker;
import fr.cyu.rt.control.persistence.PersistencePackageMarker;
import fr.cyu.rt.control.security.SecurityPackageMarker;
import fr.cyu.rt.control.services.ServicePackageMarker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
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

	public static void main(String[] args) {
		SpringApplication.run(ControlApplication.class, args);
	}

}
