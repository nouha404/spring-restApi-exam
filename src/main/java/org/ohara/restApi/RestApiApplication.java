package org.ohara.restApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })

//@ComponentScan(basePackages = {"org.ohara.restApi", "org.ohara.restApi.config"})
@SpringBootApplication()
@EntityScan(basePackages = {
		"org.ohara.maVraiDep.data.entitties",
		"org.ohara.maVraiDep.data.security.data.entities"
})
public class RestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiApplication.class, args);
	}

}
