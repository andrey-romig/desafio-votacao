package br.com.cooperativa.pauta.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "br.com.cooperativa")
@EnableScheduling
@EnableFeignClients
@EnableJpaRepositories(basePackages = "br.com.cooperativa.pauta.v1.repository")
@EntityScan("br.com.cooperativa.pauta.v1.entity")
public class PautaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PautaApplication.class, args);
	}

}
