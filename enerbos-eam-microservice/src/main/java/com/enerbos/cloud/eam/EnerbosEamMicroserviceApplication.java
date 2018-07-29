package com.enerbos.cloud.eam;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing
public class EnerbosEamMicroserviceApplication extends RepositoryRestConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(EnerbosEamMicroserviceApplication.class, args);
	}
}
