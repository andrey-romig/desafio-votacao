package br.com.cooperativa.pauta.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PautaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PautaApplication.class, args);
	}

}
