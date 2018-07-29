package com.enerbos.cloud.eam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients({ "com.enerbos.cloud.eam", "com.enerbos.cloud.uas", "com.enerbos.cloud.ams", "com.enerbos.cloud.wfs", "com.enerbos.cloud.tts" })
@EnableResourceServer
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class}) //禁用数据源
@ComponentScan({ "com.enerbos.cloud.eam", "com.enerbos.cloud.uas","com.enerbos.cloud.ams", "com.enerbos.cloud.wfs", "com.enerbos.cloud.tts"  })
@EnableSwagger2
public class EnerbosEamOpenserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnerbosEamOpenserviceApplication.class, args);
    }

}
