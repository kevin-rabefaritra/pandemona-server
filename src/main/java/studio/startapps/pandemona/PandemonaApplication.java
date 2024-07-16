package studio.startapps.pandemona;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PandemonaApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(PandemonaApplication.class);
		application.run(args);
	}
}
