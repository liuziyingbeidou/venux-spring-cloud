package org.venux.spring.cloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class VenuxEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VenuxEurekaServerApplication.class, args);
	}
}
