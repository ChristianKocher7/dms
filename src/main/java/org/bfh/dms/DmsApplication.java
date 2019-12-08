package org.bfh.dms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring boot application
 * The application context is started and created here. The Entire application is built on the Onion Architecture
 * principal. This means we have 'Core', 'API' and 'Infrastructure' packages. The core package represents our domain
 * and the DTO objects for transferring this information between services and interfaces. Within the core package we
 * also have utility classes, domain mappers and the repository interfaces.
 * In the API package are the services which create the layer between the infrastructure package and the core package.
 * These services contain the main business logic of the application.
 * The infrastructure package is the layer which allows communication from the outside of the application such as REST interfaces
 * and provides the repository implementations for our domain. Our JPA interfaces are also contained here.
 */
@SpringBootApplication
public class DmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DmsApplication.class, args);
	}

}
