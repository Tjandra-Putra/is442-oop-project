package gs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan(
		basePackages = {"gs.controller", "gs.model", "gs.repository", "gs.service", "gs.config"})
@ComponentScan(basePackages = {"gs.controller", "gs.model", "gs.repository", "gs.service", "gs.config"})
@RestController
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
