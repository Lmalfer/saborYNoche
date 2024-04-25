package es.laura.saborYNoche;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "es.laura")
public class SaborYNocheApplication {

	public static void main(String[] args) {

		SpringApplication.run(SaborYNocheApplication.class, args);
	}

}
